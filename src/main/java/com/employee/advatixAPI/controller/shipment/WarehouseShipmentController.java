package com.employee.advatixAPI.controller.shipment;

import com.employee.advatixAPI.dto.ShipmentLabel.BoxRequestDto;
import com.employee.advatixAPI.dto.ShipmentLabel.ShipmentResponseDto;
import com.employee.advatixAPI.dto.warehouseBox.BoxRequest;
import com.employee.advatixAPI.exception.NotFoundException;
import com.employee.advatixAPI.service.warehouse.WarehouseBoxLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/warehouse/shipment")
public class WarehouseShipmentController {

    @Autowired
    WarehouseBoxLabelService warehouseBoxLabelsService;

    @PostMapping("/boxLabelgenerate")
    public ResponseEntity<?> generateShipmentResponse(@RequestBody BoxRequestDto boxRequest){
        try{
            return warehouseBoxLabelsService.generateBoxLabel(boxRequest);
        }catch (NotFoundException notFoundException){
            return new ResponseEntity<>(notFoundException.getMessage(), HttpStatus.NOT_FOUND) ;
        }
    }
}
