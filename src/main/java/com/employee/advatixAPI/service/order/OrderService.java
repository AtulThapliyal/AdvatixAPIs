package com.employee.advatixAPI.service.order;

import com.employee.advatixAPI.dto.order.OrderDetailsDto;
import com.employee.advatixAPI.dto.order.OrderListRequestDto;
import com.employee.advatixAPI.dto.order.OrderRequestDto;
import com.employee.advatixAPI.entity.Carrier.ClientCarrierInfo;
import com.employee.advatixAPI.entity.Carrier.PartnerInfo;
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
import com.employee.advatixAPI.repository.Warehouse.WarehouseRepository;
import com.employee.advatixAPI.repository.partner.ClientCarrier;
import com.employee.advatixAPI.repository.partner.PartnerRepository;
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

    @Autowired
    ClientCarrier clientCarrierRepository;

    @Autowired
    PartnerRepository partnerRepository;

    public ResponseEntity<?> createOrder(OrderRequestDto orderInfo) {
        List<Integer> productIds = new ArrayList<>();

        orderInfo.getOrderItemsList().forEach(a -> productIds.add(a.getProductId()));

        Optional<List<WarehouseReceivedItems>> warehouseReceivedItems = warehouseRepository.findAllByClientIdAndProductIdIn(orderInfo.getClientId(), productIds);
        HashMap<Integer, WarehouseReceivedItems> itemsHashMap = new HashMap<>();


        CILOrderInfo cilOrder = new CILOrderInfo();

        if (warehouseReceivedItems.isPresent()) {
            warehouseReceivedItems.get().forEach(item -> itemsHashMap.put(item.getProductId(), item));

            ClientInfo clientInfo = clientRepository.findById(orderInfo.getClientId()).get();

            Optional<ClientCarrierInfo> carrierInfo = clientCarrierRepository.findByClientId(clientInfo.getClientId());
            List<CILOrderItems> orderItems = new ArrayList<>();

            cilOrder.setStatusId(0);
            cilOrder.setClientId(orderInfo.getClientId());
            cilOrder.setShipToCountryId(orderInfo.getCountryId());
            cilOrder.setShipToStateId(orderInfo.getStateId());
            cilOrder.setShipToCityId(orderInfo.getCityId());
            cilOrder.setShipToAddress(orderInfo.getAddress1());
            cilOrder.setWarehouseId(orderInfo.getWarehouseId());
            cilOrder.setShipToName(orderInfo.getShipToName());
            cilOrder.setPostalCode(orderInfo.getPostalCode());
            cilOrder.setPhoneNumber(orderInfo.getPhoneNumber());
            cilOrder.setIsResidential(orderInfo.getIsResidential());
            cilOrder.setEmailAddress(orderInfo.getEmailAddress());

            for (OrderListRequestDto cilOrderItem : orderInfo.getOrderItemsList()) {
                CILOrderItems cilOrderItems = new CILOrderItems();
                cilOrderItems.setProductId(cilOrderItem.getProductId());
                cilOrderItems.setProductQty(cilOrderItem.getProductQty());

                orderItems.add(cilOrderItems);
            }
            cilOrder.setOrderItemsList(orderItems);

            if (carrierInfo.isPresent()) {
                PartnerInfo partnerInfo = partnerRepository.findByPartnerId(carrierInfo.get().getPartnerId());
                cilOrder.setCarrierId(carrierInfo.get().getPartnerId());
                cilOrder.setCarrierName(partnerInfo.getPartnerName());
                cilOrder.setServiceType(partnerInfo.getServiceType());
            }

            CILOrderInfo orderInformation = cilOrderRepository.save(cilOrder);

            orderInformation.setOrderNumber(generateOrderNumber(orderInformation.getOrderId()));

            //checking the addresses like country city state

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
                            CILOrderItems orderItem = new CILOrderItems();

                            orderItem.setProductId(cilOrderItem.getProductId());
                            orderItem.setProductQty(warehouseInventory);
                            newOrderItems.add(orderItem);
                        }
                    } else {
                        CILOrderItems orderItem = new CILOrderItems();

                        orderItem.setProductId(cilOrderItem.getProductId());
                        orderItem.setProductQty(cilOrderItem.getProductQty());
                        newOrderItems.add(orderItem);
                    }

                }

                if (!orderItemsBackOrder.isEmpty()) {
                    cilOrderInfoBackOrder.setClientId(cilOrder.getClientId());

                    //setting the back order list in database with status 18(BackOrder)
                    cilOrderInfoBackOrder.setOrderItemsList(orderItemsBackOrder);

                    cilOrderInfoBackOrder.setStatusId(18);
                    cilOrderInfoBackOrder.setShipToCountryId(orderInfo.getCountryId());
                    cilOrderInfoBackOrder.setShipToStateId(orderInfo.getStateId());
                    cilOrderInfoBackOrder.setShipToCityId(orderInfo.getCityId());
                    cilOrderInfoBackOrder.setCarrierId(cilOrder.getCarrierId());
                    cilOrderInfoBackOrder.setShipToAddress(orderInfo.getAddress1());
                    cilOrderInfoBackOrder.setWarehouseId(orderInfo.getWarehouseId());
                    cilOrderInfoBackOrder.setShipToName(orderInfo.getShipToName());
                    cilOrderInfoBackOrder.setPostalCode(orderInfo.getPostalCode());
                    cilOrderInfoBackOrder.setPhoneNumber(orderInfo.getPhoneNumber());
                    cilOrderInfoBackOrder.setIsResidential(orderInfo.getIsResidential());
                    cilOrderInfoBackOrder.setEmailAddress(orderInfo.getEmailAddress());


                    if (carrierInfo.isPresent()) {
                        PartnerInfo partnerInfo = partnerRepository.findByPartnerId(carrierInfo.get().getPartnerId());
                        cilOrderInfoBackOrder.setCarrierId(carrierInfo.get().getPartnerId());
                        cilOrderInfoBackOrder.setCarrierName(partnerInfo.getPartnerName());
                        cilOrderInfoBackOrder.setServiceType(partnerInfo.getServiceType());
                    }
                    cilOrderInfoBackOrder.setOrderNumber(generateSplitOrderNumber(orderInformation.getOrderId()));

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

    private void saveInFEP(CILOrderInfo orderInfo, List<CILOrderItems> orderItems, HashMap<Integer, WarehouseReceivedItems> itemsHashMap) {
        FEPOrderInfo fepOrderInfo = new FEPOrderInfo();
        List<FEPOrderItems> fepOrderItemsList = new ArrayList<>();
        fepOrderInfo.setClientId(orderInfo.getClientId());
        fepOrderInfo.setOrderId(orderInfo.getOrderId());
        fepOrderInfo.setShipToCityId(orderInfo.getShipToCityId());
        fepOrderInfo.setShipToCountryId(orderInfo.getShipToCountryId());
        fepOrderInfo.setShipToStateId(orderInfo.getShipToStateId());
        fepOrderInfo.setStatusId(1);
        fepOrderInfo.setWarehouseId(orderInfo.getWarehouseId());
        fepOrderInfo.setOrderNumber(orderInfo.getOrderNumber());
        fepOrderInfo.setShipToAddress(orderInfo.getShipToAddress());
        fepOrderInfo.setShipToName(orderInfo.getShipToName());
        fepOrderInfo.setPostalCode(orderInfo.getPostalCode());
        fepOrderInfo.setEmail(orderInfo.getEmailAddress());
        fepOrderInfo.setIsResidential(orderInfo.getIsResidential());
        fepOrderInfo.setPhone(orderInfo.getPhoneNumber());


        for (int i = 0; i < orderItems.size(); i++) {
            CILOrderItems cilOrderItem = orderItems.get(i);
            fepOrderItemsList.add(new FEPOrderItems(cilOrderItem.getProductId(), cilOrderItem.getProductQty()));
            WarehouseReceivedItems item = itemsHashMap.get(cilOrderItem.getProductId());
            item.setQuantity(item.getQuantity() - cilOrderItem.getProductQty());
            warehouseRepository.updateQuantityByProductId(item.getQuantity(), item.getProductId());
        }

        fepOrderInfo.setOrderItemsList(fepOrderItemsList);
        fepOrderInfo.setCarrierId(orderInfo.getCarrierId());
        fepOrderInfo.setCarrierName(orderInfo.getCarrierName());
        fepOrderInfo.setServiceType(orderInfo.getServiceType());
        fepOrderRepository.save(fepOrderInfo);
    }

    public String generateOrderNumber(Integer orderId){
        return "ORDER"+orderId;
    }

    public String generateSplitOrderNumber(Integer orderId){
        return "ORDER"+orderId+"S1";
    }

    public ResponseEntity<?> getOrderDetails(String orderNumber) {
        OrderDetailsDto orderDetails = new OrderDetailsDto();

       Optional<FEPOrderInfo> fepOrderInfo =  fepOrderRepository.findByOrderNumber(orderNumber);
       if(fepOrderInfo.isPresent()){
           orderDetails.setOrderNumber(fepOrderInfo.get().getOrderNumber());
           orderDetails.setProductDetails(fepOrderInfo.get().getOrderItemsList());

           return ResponseEntity.ok(orderDetails);
       }else
       {
           throw new NotFoundException("The Order number does not exist" + orderNumber);
       }
    }
}

