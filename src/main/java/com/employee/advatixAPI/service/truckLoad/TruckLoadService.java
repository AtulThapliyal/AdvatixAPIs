package com.employee.advatixAPI.service.truckLoad;

import com.employee.advatixAPI.dto.truckLoad.GetOrdersRTS;
import com.employee.advatixAPI.dto.truckLoad.OrderShipmentRequest;
import com.employee.advatixAPI.entity.Order.FEPOrderInfo;
import com.employee.advatixAPI.entity.Order.FEPOrderStatus;
import com.employee.advatixAPI.entity.rooms.CarrierRooms;
import com.employee.advatixAPI.repository.Order.FEPOrderRepository;
import com.employee.advatixAPI.repository.Order.OrderStatusRepository;
import com.employee.advatixAPI.repository.carrier.CarrierRoomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class TruckLoadService {

    @Autowired
    FEPOrderRepository fepOrderRepository;

    @Autowired
    OrderStatusRepository orderStatusRepository;

    @Autowired
    CarrierRoomsRepository carrierRoomsRepository;


    public List<GetOrdersRTS> getAllOrdersForLoad() {

        List<FEPOrderInfo> orderInfos = fepOrderRepository.findAllByStatusId(6);
        List<GetOrdersRTS> ordersRTSList = new ArrayList<>();
        orderInfos.forEach(order -> {
            GetOrdersRTS ordersRTS = new GetOrdersRTS();

            ordersRTS.setOrderNumber(order.getOrderNumber());
            ordersRTS.setPhone(order.getPhone());
            ordersRTS.setEmail(order.getEmail());
            ordersRTS.setServiceType(order.getServiceType());
            ordersRTS.setCarrierName(order.getCarrierName());
            order.setWarehouseId(order.getWarehouseId());
            ordersRTS.setCarrierId(order.getCarrierId());
            ordersRTS.setStatusId(order.getStatusId());
            FEPOrderStatus status = orderStatusRepository.findById(order.getStatusId()).get();
            ordersRTS.setStatus(status.getStatusDesc());
            ordersRTS.setTotalWeight(order.getTotalWeight());
            ordersRTS.setTotalQuantity(order.getTotalQuantity());

            ordersRTSList.add(ordersRTS);
        });
        return ordersRTSList;
    }


    public String addOrderInTruck(OrderShipmentRequest orderShipmentRequest) {

        Optional<FEPOrderInfo> orderInfo = fepOrderRepository.findByOrderNumber(orderShipmentRequest.getOrderNumber());
        if (orderInfo.isPresent()) {
            Optional<CarrierRooms> carrierRooms = carrierRoomsRepository.findByRoomId(orderShipmentRequest.getRoomId());
            if (carrierRooms.isPresent()) {
                if(orderInfo.get().getCarrierId().equals(carrierRooms.get().getCarrierId())){
                    return "Order is Shipped successfully";
                }
                return "The order number " + orderShipmentRequest.getOrderNumber() +" does not belong to this carrier";
            }
            return "Not Found with Room Number" + orderShipmentRequest.getRoomId();
        }
        return "Not Found with order Number" + orderShipmentRequest.getOrderNumber();
    }
}
