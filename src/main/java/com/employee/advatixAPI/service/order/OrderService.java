package com.employee.advatixAPI.service.order;

import com.employee.advatixAPI.dto.order.OrderListRequestDto;
import com.employee.advatixAPI.dto.order.OrderRequestDto;
import com.employee.advatixAPI.entity.Client.ClientInfo;
import com.employee.advatixAPI.entity.Order.*;
import com.employee.advatixAPI.entity.warehouse.WarehouseReceivedItems;
import com.employee.advatixAPI.exception.NotFoundException;
import com.employee.advatixAPI.repository.ClientRepo.CityRepository;
import com.employee.advatixAPI.repository.ClientRepo.ClientRepository;
import com.employee.advatixAPI.repository.ClientRepo.CountryRepository;
import com.employee.advatixAPI.repository.ClientRepo.StateRepository;
import com.employee.advatixAPI.repository.Order.CILOrderRepository;
import com.employee.advatixAPI.repository.Order.FEPOrderRepository;
import com.employee.advatixAPI.service.warehouse.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    CILOrderRepository cilOrderRepository;

    @Autowired
    FEPOrderRepository fepOrderRepository;

    @Autowired
    WarehouseRepository warehouseRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    StateRepository stateRepository;

    @Autowired
    CityRepository cityRepository;


    public ResponseEntity<?> generateOrder(CILOrderInfo orderInfo) {

        //make a array list to save product Id to get from data at one using In query
        List<Integer> productIds = new ArrayList<>();

        orderInfo.getOrderItemsList().forEach(a -> productIds.add(a.getProductId()));

        //get warehouse items from the database
        Optional<List<WarehouseReceivedItems>> warehouseReceivedItems = warehouseRepository.findAllByClientIdAndProductIdIn(orderInfo.getClientId(), productIds);
        HashMap<Integer, WarehouseReceivedItems> itemsHashMap = new HashMap<>();

        if (warehouseReceivedItems.isPresent()) {
            warehouseReceivedItems.get().forEach(item -> itemsHashMap.put(item.getProductId(), item));

            ClientInfo clientInfo = clientRepository.findById(orderInfo.getClientId()).get();


            //saved the order
            CILOrderInfo cilOrder = cilOrderRepository.save(orderInfo);

            if (clientInfo.getIsPartialAllowed()) {
                CILOrderInfo cilOrderInfoBackOrder = new CILOrderInfo();
                List<CILOrderItems> orderItemsBackOrder = new ArrayList<>();

                List<CILOrderItems> orderItems = new ArrayList<>();

                for (CILOrderItems cilOrderItem : orderInfo.getOrderItemsList()) {
                    Integer warehouseInventory = itemsHashMap.get(cilOrderItem.getProductId()).getQuantity();
                    if (cilOrderItem.getProductQty() > warehouseInventory) {
                        CILOrderItems backOrderItem = new CILOrderItems();
                        backOrderItem.setProductId(cilOrderItem.getProductId());
                        backOrderItem.setProductQty(cilOrderItem.getProductQty() - warehouseInventory);
                        orderItemsBackOrder.add(backOrderItem);

                        if (warehouseInventory > 0) {
                            CILOrderItems orderItem = new CILOrderItems();
                            orderItem.setProductId(cilOrderItem.getProductId());
                            orderItem.setProductQty(warehouseInventory);
                            orderItems.add(orderItem);
                        }
                    } else {
                        CILOrderItems orderItem = new CILOrderItems();
                        orderItem.setProductId(cilOrderItem.getProductId());
                        orderItem.setProductQty(cilOrderItem.getProductQty());
                        orderItems.add(orderItem);
                    }

                }

                if (!orderItemsBackOrder.isEmpty()) {
                    cilOrderInfoBackOrder.setClientId(cilOrder.getClientId());
                    cilOrderInfoBackOrder.setOrderItemsList(orderItemsBackOrder);
                    cilOrderInfoBackOrder.setStatusId(18);
                    cilOrderRepository.save(cilOrderInfoBackOrder);
                }

                if (!orderItems.isEmpty()) {
                    saveInFEP(cilOrder, orderItems, itemsHashMap);
                }
            } else {
                for (CILOrderItems cilOrderItem : orderInfo.getOrderItemsList()) {
                    if (cilOrderItem.getProductQty() <= 0) {
                        throw new NotFoundException("Product with id  " + cilOrderItem.getProductId() + "can not be zero.");
                    } else if (cilOrderItem.getProductQty() > itemsHashMap.get(cilOrderItem.getProductId()).getQuantity()) {
                        cilOrder.setReason("The order can not be completed due to unavailability of products.");
                        return ResponseEntity.ok(cilOrder);
                    }
                }
            }
        } else {
            throw new NotFoundException("This id does not belong to this client");
        }
        return null;
    }

    private void saveInFEP(CILOrderInfo orderInfo, List<CILOrderItems> orderItems, HashMap<Integer, WarehouseReceivedItems> itemsHashMap) {
        FEPOrderInfo fepOrderInfo = new FEPOrderInfo();
        List<FEPOrderItems> fepOrderItemsList = new ArrayList<>();
        fepOrderInfo.setClientId(orderInfo.getClientId());
        fepOrderInfo.setOrderId(orderInfo.getOrderId());

        for (int i = 0; i < orderItems.size(); i++) {
            CILOrderItems cilOrderItem = orderItems.get(i);
            fepOrderItemsList.add(new FEPOrderItems(cilOrderItem.getProductId(), cilOrderItem.getProductQty()));
            WarehouseReceivedItems item = itemsHashMap.get(cilOrderItem.getProductId());
            item.setQuantity(item.getQuantity() - cilOrderItem.getProductQty());
            warehouseRepository.updateQuantityByProductId(item.getQuantity(), item.getProductId());
        }

        fepOrderInfo.setOrderItemsList(fepOrderItemsList);
        fepOrderRepository.save(fepOrderInfo);
    }

    public ResponseEntity<?> createOrder(OrderRequestDto orderInfo) {
        List<Integer> productIds = new ArrayList<>();

        orderInfo.getOrderItemsList().forEach(a -> productIds.add(a.getProductId()));

        Optional<List<WarehouseReceivedItems>> warehouseReceivedItems = warehouseRepository.findAllByClientIdAndProductIdIn(orderInfo.getClientId(), productIds);
        HashMap<Integer, WarehouseReceivedItems> itemsHashMap = new HashMap<>();

        CILOrderInfo cilOrder = new CILOrderInfo();
        CILOrderItems cilOrderItems = new CILOrderItems();

        if (warehouseReceivedItems.isPresent()) {
            warehouseReceivedItems.get().forEach(item -> itemsHashMap.put(item.getProductId(), item));

            ClientInfo clientInfo = clientRepository.findById(orderInfo.getClientId()).get();
            List<CILOrderItems> orderItems = new ArrayList<>();
            cilOrder.setStatusId(0);
            cilOrder.setClientId(orderInfo.getClientId());
            cilOrder.setCountryId(orderInfo.getCountryId());
            cilOrder.setStateId(orderInfo.getStateId());
            cilOrder.setCityId(orderInfo.getCityId());
            for (OrderListRequestDto cilOrderItem : orderInfo.getOrderItemsList()) {
                cilOrderItems.setProductId(cilOrderItem.getProductId());
                cilOrderItems.setProductQty(cilOrderItem.getProductQty());

                orderItems.add(cilOrderItems);
            }
            cilOrder.setOrderItemsList(orderItems);
            cilOrder.setCarrierId(1);
            cilOrder.setCarrierName("FEDEX");
            cilOrder.setServiceType("1 day Delivery");

            CILOrderInfo orderInformation = cilOrderRepository.save(cilOrder);

            if (clientInfo.getIsPartialAllowed()) {
                CILOrderInfo cilOrderInfoBackOrder = new CILOrderInfo();
                List<CILOrderItems> orderItemsBackOrder = new ArrayList<>();

                List<CILOrderItems> newOrderItems = new ArrayList<>();

                for (CILOrderItems cilOrderItem : orderInformation.getOrderItemsList()) {
                    Integer warehouseInventory = itemsHashMap.get(cilOrderItem.getProductId()).getQuantity();
                    if (cilOrderItem.getProductQty() > warehouseInventory) {
                        CILOrderItems backOrderItem = new CILOrderItems();
                        backOrderItem.setProductId(cilOrderItem.getProductId());
                        backOrderItem.setProductQty(cilOrderItem.getProductQty() - warehouseInventory);
                        orderItemsBackOrder.add(backOrderItem);

                        if (warehouseInventory > 0) {
                            cilOrderItems.setProductId(cilOrderItem.getProductId());
                            cilOrderItems.setProductQty(warehouseInventory);
                            newOrderItems.add(cilOrderItems);
                        }
                    } else {
                        cilOrderItems.setProductId(cilOrderItem.getProductId());
                        cilOrderItems.setProductQty(cilOrderItem.getProductQty());
                        newOrderItems.add(cilOrderItems);
                    }

                }

                if (!orderItemsBackOrder.isEmpty()) {
                    cilOrderInfoBackOrder.setClientId(cilOrder.getClientId());
                    cilOrderInfoBackOrder.setOrderItemsList(orderItemsBackOrder);
                    cilOrderInfoBackOrder.setStatusId(18);
                    cilOrderInfoBackOrder.setCountryId(orderInfo.getCountryId());
                    cilOrderInfoBackOrder.setStateId(orderInfo.getStateId());
                    cilOrderInfoBackOrder.setCityId(orderInfo.getCityId());
                    cilOrderInfoBackOrder.setCarrierId(1);
                    cilOrderInfoBackOrder.setCarrierName("FEDEX");
                    cilOrderInfoBackOrder.setServiceType("1 day Delivery");
                    cilOrderRepository.save(cilOrderInfoBackOrder);
                }

                if (!newOrderItems.isEmpty()) {
                    saveInFEP(cilOrder, newOrderItems, itemsHashMap);
                }


            } else {
                for (OrderListRequestDto cilOrderItem : orderInfo.getOrderItemsList()) {
                    if (cilOrderItem.getProductQty() <= 0) {
                        throw new NotFoundException("Product with id  " + cilOrderItem.getProductId() + "can not be zero.");
                    } else if (cilOrderItem.getProductQty() > itemsHashMap.get(cilOrderItem.getProductId()).getQuantity()) {
                        orderInformation.setReason("The order can not be completed due to unavailability of products.");
                        return ResponseEntity.ok(orderInformation);
                    }
                    saveInFEP(cilOrder, orderItems, itemsHashMap);
                }
            }
        } else {
            throw new NotFoundException("This id does not belong to this client");
        }

        return null;
    }

}
