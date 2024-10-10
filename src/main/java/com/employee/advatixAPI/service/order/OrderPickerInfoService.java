package com.employee.advatixAPI.service.order;

import com.employee.advatixAPI.dto.order.ContainerProducts;
import com.employee.advatixAPI.dto.order.Containers;
import com.employee.advatixAPI.dto.order.OrderPickerDto;
import com.employee.advatixAPI.entity.Order.FEPOrderInfo;
import com.employee.advatixAPI.entity.Order.OrderPickerInfo;
import com.employee.advatixAPI.entity.warehouse.enums.Status;
import com.employee.advatixAPI.exception.NotFoundException;
import com.employee.advatixAPI.repository.Order.FEPOrderRepository;
import com.employee.advatixAPI.repository.Order.OrderPickerInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;

@Service
public class OrderPickerInfoService {
    @Autowired
    OrderPickerInfoRepository orderPickerInfoRepository;

    @Autowired
    FEPOrderRepository fepOrderRepository;


    //saving only the picker not container
//    public ResponseEntity<?> assignPicker(OrderPickerDto orderPickerDto) {
//
//        //find the order with the order id in fep layer.
//        FEPOrderInfo fepOrderInfo = fepOrderRepository.findById(orderPickerDto.getOrderId()).orElseThrow(() -> new NotFoundException("No id found with " + orderPickerDto.getOrderId()));
//
//        //get the order information if already present in order picker table
//        Optional<OrderPickerInfo> orderPickerInformation = orderPickerInfoRepository.findByOrderNumber(fepOrderInfo.getOrderNumber());
//
//       //if present update the updated on day and picker name as all other fields can not be changed.
//        if (orderPickerInformation.isPresent()) {
//            orderPickerInformation.get().setPickerName(orderPickerDto.getPickerName());
//            orderPickerInformation.get().setUpdatedOn(LocalDate.now());
//
//            orderPickerInfoRepository.save(orderPickerInformation.get());
//            return ResponseEntity.ok(orderPickerInformation);
//
//        }
//
//        //if no order found in order picker generate a new record and insert into it
//        OrderPickerInfo orderPickerInfo = new OrderPickerInfo();
//
//        orderPickerInfo.setOrderNumber(fepOrderInfo.getOrderNumber());
//        orderPickerInfo.setPickerName(orderPickerDto.getPickerName());
//        orderPickerInfo.setStatus(Status.PICKED);
//        orderPickerInfo.setCreatedOn(LocalDate.now());
//        orderPickerInfo.setUpdatedOn(LocalDate.now());
//
//        orderPickerInfoRepository.save(orderPickerInfo);
//
//        //change the status id to 3 that is picked.
//        fepOrderInfo.setStatusId(3);
//        fepOrderRepository.save(fepOrderInfo);
//
//        return ResponseEntity.ok(orderPickerInfo);
//    }

    public ResponseEntity<?> assignPicker(OrderPickerDto orderPickerDto) {

        //find the order with the order id in fep layer.
        FEPOrderInfo fepOrderInfo = fepOrderRepository.findById(orderPickerDto.getOrderId()).orElseThrow(() -> new NotFoundException("No id found with " + orderPickerDto.getOrderId()));

        //get the product quantity in hasmap
        HashMap<Integer, Integer> originalProductsList = new HashMap<>();
        fepOrderInfo.getOrderItemsList().forEach(originalProduct->{
            originalProductsList.put(originalProduct.getProductId(), originalProduct.getProductQty());
        });

        orderPickerDto.getContainersList().forEach(containers -> {
            containers.getContainerProductsList().forEach(containerProducts -> {
                Integer productId = containerProducts.getProductId();
                Integer originalQty = originalProductsList.get(productId);
                Integer currentQty = containerProducts.getQuantity();

                if (originalQty == null) {
                    throw new NotFoundException("Product with ID " + productId + " is not found in the original order");
                }

                // Update the original product quantity by subtracting user-picked quantity
                originalProductsList.compute(productId, (k, qty) -> (qty == null) ? -currentQty : qty - currentQty);

                // Check if the picked quantity exceeds the original quantity
                if (originalProductsList.get(productId) < 0) {
                    throw new NotFoundException("The quantity of product " + productId + " exceeds the order quantity");
                }
            });
        });

        originalProductsList.forEach((productId, remainingQty) -> {
            if (remainingQty > 0) {
                throw new NotFoundException("The quantity of product " + productId + " is less than the order quantity");
            }
        });

        orderPickerDto.getContainersList().forEach(containers -> {
            containers.getContainerProductsList().forEach(containerProducts -> {
                OrderPickerInfo orderPickerInfo = generateOrderPickerItems(orderPickerDto, fepOrderInfo, containerProducts, containers);
                orderPickerInfoRepository.save(orderPickerInfo);
            });
        });

        fepOrderInfo.setStatusId(3);
        fepOrderRepository.save(fepOrderInfo);
        return ResponseEntity.ok("Order picked Successfully !!");
    }

    public OrderPickerInfo generateOrderPickerItems(OrderPickerDto orderPickerDto, FEPOrderInfo fepOrderInfo, ContainerProducts containerProducts, Containers containers){
        OrderPickerInfo orderPickerInfo = new OrderPickerInfo();

        orderPickerInfo.setOrderNumber(fepOrderInfo.getOrderNumber());
        orderPickerInfo.setPickerName(orderPickerDto.getPickerName());
        orderPickerInfo.setProductId(containerProducts.getProductId());
        orderPickerInfo.setProductQty(containerProducts.getQuantity());
        orderPickerInfo.setContainerId(containers.getContainerId());
        orderPickerInfo.setStatus(Status.PICKED);
        orderPickerInfo.setCreatedOn(LocalDate.now());
        orderPickerInfo.setUpdatedOn(LocalDate.now());

        return  orderPickerInfo;
    }


}
