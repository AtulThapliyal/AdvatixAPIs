package com.employee.advatixAPI.service.Lpn.LpnService;

import com.employee.advatixAPI.dto.Lpn.LicensePlateNumberRequest;
import com.employee.advatixAPI.dto.Lpn.LpnOrders;
import com.employee.advatixAPI.entity.lpn.LpnInfo;
import com.employee.advatixAPI.entity.lpn.OrderLpnInfo;
import com.employee.advatixAPI.entity.warehouse.Warehouse;
import com.employee.advatixAPI.entity.warehouse.WarehouseAddressEntity;
import com.employee.advatixAPI.repository.Lpn.LpnInfoRepository;
import com.employee.advatixAPI.repository.Lpn.LpnOrderRespository;
import com.employee.advatixAPI.repository.Warehouse.WarehouseAddressRepository;
import com.employee.advatixAPI.repository.Warehouse.WhRepository;
import com.employee.advatixAPI.repository.partner.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class LpnService {

    @Autowired
    LpnOrderRespository orderLpnInfoRepository;

    @Autowired
    LpnInfoRepository lpnInfoRepository;

    @Autowired
    WarehouseAddressRepository warehouseRepository;

    @Autowired
    PartnerRepository partnerRepository;


    public String addOrderLpn(LpnOrders lpnOrders) {
        OrderLpnInfo orderLpnInfo = new OrderLpnInfo();
        Optional<LpnInfo> lpnInfo = lpnInfoRepository.findByLpnNumber(lpnOrders.getLpnNumber());
        if (lpnInfo.isPresent()) {
            if (lpnInfo.get().getStatus() == 2 || lpnInfo.get().getStatus() == 3) {
                return "LPN not available";
            }
            lpnOrders.getOrdersList().forEach(lpnOrder -> {
                orderLpnInfo.setLpnNumber(lpnOrders.getLpnNumber());
                orderLpnInfo.setOrderNumber(lpnOrder);

                orderLpnInfoRepository.save(orderLpnInfo);
            });

            lpnInfo.get().setStatus(2);
            lpnInfoRepository.save(lpnInfo.get());

            return "Order Mapped with the Licence Plate Number" + lpnOrders.getLpnNumber();
        }
        return "No License Plate Number found !!";
    }

    public String createLpnNumber(LicensePlateNumberRequest licensePlateNumberRequest) {
        LpnInfo lpnInfo = new LpnInfo();
        Optional<WarehouseAddressEntity> warehouse = warehouseRepository.findById(licensePlateNumberRequest.getWarehouseId());

        partnerRepository.findByPartnerId(licensePlateNumberRequest.getShipperId());
        if (warehouse.isPresent()) {
            lpnInfo.setStatus(1);
            lpnInfo.setShipToAddress(partnerRepository.findByPartnerId(licensePlateNumberRequest.getShipperId()).getPartnerName());
            lpnInfo.setShipFromAddress(warehouse.get().getAddress1());
            lpnInfo.setWarehouseId(licensePlateNumberRequest.getWarehouseId());
            SimpleDateFormat obj = new SimpleDateFormat("YYYYMMDDHHmmss");
            String dd = obj.format(new Date());
            lpnInfo.setLpnNumber("LPN" + dd);
            LpnInfo savedInfo = lpnInfoRepository.save(lpnInfo);
            return savedInfo.getLpnNumber();
        }
        return "No warehouse found with id " + licensePlateNumberRequest.getWarehouseId();
    }
}
