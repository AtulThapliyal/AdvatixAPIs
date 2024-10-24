package com.employee.advatixAPI.service.truckLoad;

import com.employee.advatixAPI.dto.truckLoad.GetOrdersRTS;
import com.employee.advatixAPI.dto.truckLoad.LpnOrders;
import com.employee.advatixAPI.dto.truckLoad.OrderShipmentRequest;
import com.employee.advatixAPI.entity.Order.FEPOrderInfo;
import com.employee.advatixAPI.entity.Order.FEPOrderStatus;
import com.employee.advatixAPI.entity.Carrier.CarrierRooms;
import com.employee.advatixAPI.entity.lpn.OrderLpnInfo;
import com.employee.advatixAPI.entity.manifest.ManifestInfo;
import com.employee.advatixAPI.entity.manifest.ManifestMapping;
import com.employee.advatixAPI.exception.NotFoundException;
import com.employee.advatixAPI.repository.Lpn.LpnOrderRespository;
import com.employee.advatixAPI.repository.Lpn.ManifestRepository;
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

    @Autowired
    LpnOrderRespository orderLpnInfoRepository;

    @Autowired
    ManifestRepository manifestRepository;


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

        switch (orderShipmentRequest.getType()) {
            case "Order":
                Optional<FEPOrderInfo> orderInfo = fepOrderRepository.findByOrderNumber(orderShipmentRequest.getNumber());
                if (orderInfo.isPresent()) {
                    Optional<CarrierRooms> carrierRooms = carrierRoomsRepository.findByRoomId(orderShipmentRequest.getRoomId());
                    if (carrierRooms.isPresent()) {
                        if (orderInfo.get().getCarrierId().equals(carrierRooms.get().getCarrierId())) {
                            orderInfo.get().setStatusId(7);
                            fepOrderRepository.save(orderInfo.get());
                            return "Order is Shipped successfully";
                        }
                        return "The order number " + orderShipmentRequest.getNumber() + " does not belong to this carrier";
                    }
                    return "Not Found with Room Number" + orderShipmentRequest.getRoomId();
                }
                throw new NotFoundException("Not Found with order Number" + orderShipmentRequest.getNumber());
            case "Lpn":
                Optional<List<OrderLpnInfo>> lpnOrders = orderLpnInfoRepository.findAllByLpnNumber(orderShipmentRequest.getNumber());

                lpnOrders.ifPresent(orderLpnInfos -> orderLpnInfos.forEach(lpnOrder -> {
                    Optional<FEPOrderInfo> order = fepOrderRepository.findByOrderNumber(lpnOrder.getOrderNumber());

                    order.get().setStatusId(7);
                    fepOrderRepository.save(order.get());
                }));
            case "Manifest":
                Optional<List<ManifestMapping>> manifestInfo = manifestRepository.findAllByManifestNumber(orderShipmentRequest.getNumber());
                manifestInfo.get().forEach(manifest -> {
                    if (manifest.getOrderNumber() != null) {
                        Optional<FEPOrderInfo> orderInformation = fepOrderRepository.findByOrderNumber(orderShipmentRequest.getNumber());
                        orderInformation.get().setStatusId(7);
                        fepOrderRepository.save(orderInformation.get());
                    }

                    if (manifest.getLpnNumber() != null) {
                        Optional<List<OrderLpnInfo>> lpns = orderLpnInfoRepository.findAllByLpnNumber(manifest.getLpnNumber());

                        lpns.ifPresent(orderLpnInfos -> orderLpnInfos.forEach(lpnOrder -> {
                            Optional<FEPOrderInfo> order = fepOrderRepository.findByOrderNumber(lpnOrder.getOrderNumber());

                            order.get().setStatusId(7);
                            fepOrderRepository.save(order.get());
                        }));
                    }
                });
        }

        return "Done";
    }
}
