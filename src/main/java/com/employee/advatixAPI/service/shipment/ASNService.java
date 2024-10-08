package com.employee.advatixAPI.service.shipment;

import com.employee.advatixAPI.dto.Shipment.ASNNoticeRequestDto;
import com.employee.advatixAPI.dto.Shipment.ASNUnitRequest;
import com.employee.advatixAPI.entity.Shipment.ASNNotice;
import com.employee.advatixAPI.entity.Shipment.ASNUnits;
import com.employee.advatixAPI.entity.warehouse.WarehouseReceivedItems;
import com.employee.advatixAPI.entity.warehouse.enums.InventoryStage;
import com.employee.advatixAPI.entity.warehouse.enums.ReceiveStatus;
import com.employee.advatixAPI.repository.EmployeeRepository;
import com.employee.advatixAPI.repository.Shipment.ASNNoticeRepository;
import com.employee.advatixAPI.repository.Shipment.ASNUnitRepository;
import com.employee.advatixAPI.repository.Warehouse.WarehouseRepository;
import com.employee.advatixAPI.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ASNService {

    @Autowired
    ASNNoticeRepository asnNoticeRepository;

    @Autowired
    ASNUnitRepository asnUnitRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    WarehouseRepository warehouseRepository;

    public ASNNotice addASN(ASNNoticeRequestDto asnNoticeRequest) {
        ASNNotice asnNotice = new ASNNotice();
//        WarehouseReceivedItems warehouseReceivedItems = new WarehouseReceivedItems();

        asnNotice.setPoNumber(asnNoticeRequest.getPoNumber());
        asnNotice.setLotNumber(asnNoticeRequest.getLotNumber());
        asnNotice.setTotalQuantity(asnNoticeRequest.getTotalQuantity());
        asnNotice.setCreatedOn(LocalDate.now());
        asnNotice.setEmployee(employeeRepository.findById(asnNoticeRequest.getCreatedBy()).get());


        List<ASNUnits> asnUnitsList = new ArrayList<>();
        for (ASNUnitRequest asnUnit : asnNoticeRequest.getAsnUnitList()) {
            ASNUnits unit = new ASNUnits(asnUnit.getQuantity(), asnUnit.getReceivedQuantity(), asnUnit.getLocation(), productRepository.findByProductId(asnUnit.getProductId()).get());
            asnUnitsList.add(unit);

            Optional<WarehouseReceivedItems> receivedItem = warehouseRepository.findWarehouseReceivedItemsByProductId(unit.getProduct().getProductId());

            if(receivedItem.isPresent()){
                receivedItem.get().setQuantity(receivedItem.get().getQuantity() + unit.getReceivedQty());
            }else {
                WarehouseReceivedItems warehouseReceivedItems = new WarehouseReceivedItems();

                warehouseReceivedItems.setWarehouseId(asnNoticeRequest.getWarehouseId());
                warehouseReceivedItems.setLocation(asnUnit.getLocation());
                warehouseReceivedItems.setClientId(unit.getProduct().getClientId());
                warehouseReceivedItems.setEmployeeId(asnNoticeRequest.getCreatedBy());
                warehouseReceivedItems.setQuantity(asnUnit.getReceivedQuantity());
                warehouseReceivedItems.setProductId(asnUnit.getProductId());
                warehouseReceivedItems.setCreatedOn(LocalDate.now());
                warehouseReceivedItems.setLotNumber(asnNoticeRequest.getLotNumber());
                warehouseReceivedItems.setInventoryStage(InventoryStage.ON_HAND);
                warehouseReceivedItems.setReceiveStatus(ReceiveStatus.RECEIVED);

                warehouseRepository.save(warehouseReceivedItems);

            }
        }
        asnNotice.setAsnUnitsList(asnUnitsList);  // set the saved ASNNotice with its units
        ASNNotice savedNotice = asnNoticeRepository.save(asnNotice);  // Save ASNNotice to return

        return savedNotice;
    }
}
