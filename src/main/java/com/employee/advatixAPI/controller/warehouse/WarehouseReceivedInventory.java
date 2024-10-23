package com.employee.advatixAPI.controller.warehouse;

import com.employee.advatixAPI.dto.order.Containers;
import com.employee.advatixAPI.dto.warehouse.Stow;
import com.employee.advatixAPI.dto.warehouse.WarehouseReceivedItemsRequest;
import com.employee.advatixAPI.service.warehouse.WarehouseItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/warehouse")
public class WarehouseReceivedInventory {

    @Autowired
    WarehouseItemsService warehouseItemsService;

    @PostMapping("/receive/inventory")
    public ResponseEntity<?> receiveInventory(@RequestBody WarehouseReceivedItemsRequest warehouseItemsRequest){
        return warehouseItemsService.receiveItemsInContainers(warehouseItemsRequest);
    }

    @PostMapping("/stow/inventory")
    public ResponseEntity<?> stowInventory(@RequestBody Stow stowRequest){
        return warehouseItemsService.stowItemsInBins(stowRequest);
    }
}
