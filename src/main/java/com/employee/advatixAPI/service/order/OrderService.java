package com.employee.advatixAPI.service.order;

import com.employee.advatixAPI.entity.Client.ClientInfo;
import com.employee.advatixAPI.entity.Order.*;
import com.employee.advatixAPI.entity.warehouse.WarehouseReceivedItems;
import com.employee.advatixAPI.exception.NotFoundException;
import com.employee.advatixAPI.repository.ClientRepo.ClientRepository;
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
            }
            else {
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

}
