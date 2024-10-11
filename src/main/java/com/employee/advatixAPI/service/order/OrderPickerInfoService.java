package com.employee.advatixAPI.service.order;

import com.employee.advatixAPI.dto.order.ContainerProductListDto;
import com.employee.advatixAPI.dto.order.ContainerProducts;
import com.employee.advatixAPI.dto.order.Containers;
import com.employee.advatixAPI.dto.order.OrderPickerDto;
import com.employee.advatixAPI.entity.Order.FEPOrderInfo;
import com.employee.advatixAPI.entity.Order.OrderPickerInfo;
import com.employee.advatixAPI.entity.warehouse.WarehouseContainers;
import com.employee.advatixAPI.entity.warehouse.enums.Status;
import com.employee.advatixAPI.exception.NotFoundException;
import com.employee.advatixAPI.repository.Order.FEPOrderRepository;
import com.employee.advatixAPI.repository.Order.OrderPickerInfoRepository;
import com.employee.advatixAPI.repository.Warehouse.WarehouseContainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class OrderPickerInfoService {
    @Autowired
    OrderPickerInfoRepository orderPickerInfoRepository;

    @Autowired
    FEPOrderRepository fepOrderRepository;


    @Autowired
    WarehouseContainerRepository warehouseContainerRepository;

    //saving only the picker not container
    public ResponseEntity<?> assignPicker(OrderPickerDto orderPickerDto) {

        //find the order with the order id in fep layer.
        FEPOrderInfo fepOrderInfo = fepOrderRepository.findById(orderPickerDto.getOrderId()).orElseThrow(() -> new NotFoundException("No id found with " + orderPickerDto.getOrderId()));

        //get the order information if already present in order picker table
        Optional<OrderPickerInfo> orderPickerInformation = orderPickerInfoRepository.findByOrderNumber(fepOrderInfo.getOrderNumber());

        //if present update the updated on day and picker name as all other fields can not be changed.
        if (orderPickerInformation.isPresent()) {
            orderPickerInformation.get().setPickerName(orderPickerDto.getPickerName());
            orderPickerInformation.get().setUpdatedOn(LocalDate.now());

            orderPickerInfoRepository.save(orderPickerInformation.get());
            return ResponseEntity.ok(orderPickerInformation);

        }

        //if no order found in order picker generate a new record and insert into it
        OrderPickerInfo orderPickerInfo = new OrderPickerInfo();

        orderPickerInfo.setOrderNumber(fepOrderInfo.getOrderNumber());
        orderPickerInfo.setPickerName(orderPickerDto.getPickerName());
        orderPickerInfo.setStatus(Status.PICKED);
        orderPickerInfo.setCreatedOn(LocalDate.now());
        orderPickerInfo.setUpdatedOn(LocalDate.now());

        orderPickerInfoRepository.save(orderPickerInfo);

        //change the status id to 3 that is picked.
        fepOrderInfo.setStatusId(3);
        fepOrderRepository.save(fepOrderInfo);

        return ResponseEntity.ok(orderPickerInfo);
    }

    public ResponseEntity<?> assignPickerContainer(OrderPickerDto orderPickerDto) {

        //find the order with the order id in fep layer.
        FEPOrderInfo fepOrderInfo = fepOrderRepository.findById(orderPickerDto.getOrderId()).orElseThrow(() -> new NotFoundException("No id found with " + orderPickerDto.getOrderId()));

        //get the product quantity in hasmap
        HashMap<Integer, Integer> originalProductsList = new HashMap<>();
        fepOrderInfo.getOrderItemsList().forEach(originalProduct -> {
            originalProductsList.put(originalProduct.getProductId(), originalProduct.getProductQty());
        });

        List<ContainerProductListDto> containerProductListDtoList = new ArrayList<>();

        orderPickerDto.getPickLists().forEach(containers -> {
            containers.getProductsList().forEach(containerProducts -> {
                Integer productId = containerProducts.getProductId();
                Integer originalQty = originalProductsList.get(productId);
                Integer currentQty = containerProducts.getQuantity();

                ContainerProductListDto productListDto = new ContainerProductListDto();

                if (originalQty == null) {
                    throw new NotFoundException("Product with ID " + productId + " is not found in the original order");
                }

                // Update the original product quantity by subtracting user-picked quantity
                originalProductsList.compute(productId, (k, qty) -> (qty == null) ? -currentQty : qty - currentQty);

                // Check if the picked quantity exceeds the original quantity
                if (originalProductsList.get(productId) < 0) {
                    throw new NotFoundException("The quantity of product " + productId + " exceeds the order quantity");
                }

                //check if container exists by the given id
                Optional<WarehouseContainers> warehouseContainers = warehouseContainerRepository.findByContainerId(containers.getId());

                if (warehouseContainers.isPresent()) {
                    //if yes then find that order number should be null  or can be same
                    if (warehouseContainers.get().getOrderNumber() == null || warehouseContainers.get().getOrderNumber().equals(fepOrderInfo.getOrderNumber())) {
                        productListDto.setContainerId(containers.getId());

                        //in container table set the order id to let the warehouse picker know that container is assisgned to the order.
                        warehouseContainers.get().setOrderNumber(fepOrderInfo.getOrderNumber());

                        //save the order number to not make further use in another order.
                        warehouseContainerRepository.save(warehouseContainers.get());
                    } else {
                        throw new NotFoundException("The container of Id " + containers.getId() + " has another order " + warehouseContainers.get().getOrderNumber());
                    }
                } else {
                    throw new NotFoundException("The container of Id " + containers.getId() + " is not present.");
                }
                productListDto.setQuantity(currentQty);
                productListDto.setProductId(productId);

                containerProductListDtoList.add(productListDto);
            });
        });

        originalProductsList.forEach((productId, remainingQty) -> {
            if (remainingQty > 0) {
                throw new NotFoundException("The quantity of product " + productId + " is less than the order quantity");
            }
        });

        containerProductListDtoList.forEach(containerProductListDto -> {
            OrderPickerInfo orderPickerInfo = generateOrderPickerItems(orderPickerDto, fepOrderInfo, containerProductListDto);
            orderPickerInfoRepository.save(orderPickerInfo);
        });

        fepOrderInfo.setStatusId(3);
        fepOrderRepository.save(fepOrderInfo);
        return ResponseEntity.ok("Order picked Successfully !!");
    }

    private OrderPickerInfo generateOrderPickerItems(OrderPickerDto orderPickerDto, FEPOrderInfo fepOrderInfo, ContainerProductListDto containerProductListDto) {
        OrderPickerInfo orderPickerInfo = new OrderPickerInfo();

        orderPickerInfo.setOrderNumber(fepOrderInfo.getOrderNumber());
        orderPickerInfo.setPickerName(orderPickerDto.getPickerName());
        orderPickerInfo.setProductId(containerProductListDto.getProductId());
        orderPickerInfo.setProductQty(containerProductListDto.getQuantity());
        orderPickerInfo.setContainerId(containerProductListDto.getContainerId());
        orderPickerInfo.setStatus(Status.PICKED);
        orderPickerInfo.setCreatedOn(LocalDate.now());
        orderPickerInfo.setUpdatedOn(LocalDate.now());

        return orderPickerInfo;
    }

    public ResponseEntity<?> assignBoxOrder(OrderPickerDto orderPickerDto) {

        FEPOrderInfo fepOrderInfo = fepOrderRepository.findById(orderPickerDto.getOrderId()).orElseThrow(() -> new NotFoundException("No id found with " + orderPickerDto.getOrderId()));

        orderPickerDto.getPickLists().forEach(box -> {
            box.getProductsList().forEach(containerProducts -> {
                OrderPickerInfo orderPickerInfo = new OrderPickerInfo();

                orderPickerInfo.setOrderNumber(fepOrderInfo.getOrderNumber());
                orderPickerInfo.setPickerName(orderPickerDto.getPickerName());
                orderPickerInfo.setProductId(containerProducts.getProductId());
                orderPickerInfo.setProductQty(containerProducts.getQuantity());
                orderPickerInfo.setBoxId(box.getId());
                orderPickerInfo.setStatus(Status.PACKED);
                orderPickerInfo.setCreatedOn(LocalDate.now());
                orderPickerInfo.setUpdatedOn(LocalDate.now());

                orderPickerInfoRepository.save(orderPickerInfo);
            });
        });

        fepOrderInfo.setStatusId(5);
        fepOrderRepository.save(fepOrderInfo);
        return ResponseEntity.ok("Order packed Successfully !!");
    }


    public String getContainer() {
//        warehouseContainerRepository.findF
        WarehouseContainers warehouseContainers = warehouseContainerRepository.findFirstByOrderNumber(null);
        return warehouseContainers.getContainerId();
    }
}
