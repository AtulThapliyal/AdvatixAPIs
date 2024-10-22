package com.employee.advatixAPI.controller.truckLoad;

import com.employee.advatixAPI.dto.truckLoad.GetOrdersRTS;
import com.employee.advatixAPI.dto.truckLoad.OrderShipmentRequest;
import com.employee.advatixAPI.entity.Order.FEPOrderInfo;
import com.employee.advatixAPI.exception.NotFoundException;
import com.employee.advatixAPI.service.truckLoad.TruckLoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/truck/load")
public class TruckLoadController {

    @Autowired
    TruckLoadService truckLoadService;

    @GetMapping("/getAllOrders")
    public ResponseEntity<?> getAllLoadOrders() {
        try {
            List<GetOrdersRTS> orders = truckLoadService.getAllOrdersForLoad();
            return ResponseEntity.ok(orders);
        } catch (NotFoundException notFoundException) {
            throw new NotFoundException(notFoundException.getMessage());
        }
    }


    @PostMapping("/addOrderInTruck")
    public ResponseEntity<?> loadAllOrders(@RequestBody OrderShipmentRequest orderShipmentRequest) {
        try {
            String added =  truckLoadService.addOrderInTruck(orderShipmentRequest);

            return ResponseEntity.ok(added);
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }
}
