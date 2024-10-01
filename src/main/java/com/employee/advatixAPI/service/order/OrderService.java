package com.employee.advatixAPI.service.order;

import com.employee.advatixAPI.entity.Order.CILOrderInfo;
import com.employee.advatixAPI.entity.Order.CILOrderItems;
import com.employee.advatixAPI.entity.Order.FEPOrderInfo;
import com.employee.advatixAPI.entity.Order.FEPOrderItems;
import com.employee.advatixAPI.entity.warehouse.WarehouseReceivedItems;
import com.employee.advatixAPI.exception.NotFoundException;
import com.employee.advatixAPI.repository.Order.CILOrderRepository;
import com.employee.advatixAPI.repository.Order.FEPOrderRepository;
import com.employee.advatixAPI.service.warehouse.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    CILOrderRepository cilOrderRepository;

    @Autowired
    FEPOrderRepository fepOrderRepository;

    @Autowired
    WarehouseRepository warehouseRepository;


    public ResponseEntity<?> generateOrder(CILOrderInfo orderInfo) {
        //first save the order in cil layer

        //make a array list to save product Id to get from data at one using In query
        List<Integer> productIds = new ArrayList<>();

        orderInfo.getOrderItemsList().forEach(a -> productIds.add(a.getProductId()));

        //get warehouse items from the database
        List<WarehouseReceivedItems> warehouseReceivedItems = warehouseRepository.findByProductIdIn(productIds);
        HashMap<Integer, WarehouseReceivedItems> itemsHashMap = new HashMap<>();

        //save the product id and the whole item in hashmap
        warehouseReceivedItems.forEach(item -> itemsHashMap.put(item.getProductId(), item));

        //check for the validation in any product and donot save it in fep if order can not be fulfilled
        CILOrderInfo cilOrder = cilOrderRepository.save(orderInfo);
        for (CILOrderItems cilOrderItem : orderInfo.getOrderItemsList()) {
            if (cilOrderItem.getProductQty() <= 0) {
                throw new NotFoundException("Product with id  " + cilOrderItem.getProductId() + "can not be zero.");
            } else if (cilOrderItem.getProductQty() > itemsHashMap.get(cilOrderItem.getProductId()).getQuantity()) {
                cilOrder.setReason("The order can not be completed due to unavailability of products.");
                return ResponseEntity.ok(cilOrder);
            }
        }


        // if the details are correct and warehouse has the ability to fulfil the order then save it in fep
        FEPOrderInfo fepOrderInfo = new FEPOrderInfo();
        List<FEPOrderItems> fepOrderItemsList = new ArrayList<>();
        fepOrderInfo.setClientId(orderInfo.getClientId());
        fepOrderInfo.setOrderId(orderInfo.getOrderId());

        // logic for reducing the quantity from the database for the particular item
        for (int i = 0 ;i < orderInfo.getOrderItemsList().size();i++){
            CILOrderItems cilOrderItem = orderInfo.getOrderItemsList().get(i);
            fepOrderItemsList.add(new FEPOrderItems(cilOrderItem.getProductId(), cilOrderItem.getProductQty()));

            //getting the data one by one
//            WarehouseReceivedItems item = warehouseRepository.findByProductId(cilOrderItem.getProductId());
//            item.setQuantity(item.getQuantity() - cilOrderItem.getProductQty());

            //getting data from hashmap and saving it in database
            WarehouseReceivedItems item = itemsHashMap.get(cilOrderItem.getProductId()) ;
            item.setQuantity(item.getQuantity() - cilOrderItem.getProductQty());
            warehouseRepository.updateQuantityByProductId(item.getQuantity(), item.getProductId());

        }


        //set the order list in fep
        fepOrderInfo.setOrderItemsList(fepOrderItemsList);

        //save the order in fep as the warehouse has the ability or have sufficient products to fulfil the order.
        fepOrderRepository.save(fepOrderInfo);

        return null;
    }

}
