package com.employee.advatixAPI.controller.warehouse;

import com.employee.advatixAPI.dto.warehouse.BoxRequest;
import com.employee.advatixAPI.exception.NotFoundException;
import com.employee.advatixAPI.service.warehouse.WarehouseBoxLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/box/label")
public class WarehouseBox {
    @Autowired
    WarehouseBoxLabelService warehouseBoxLabelsService;

    @PostMapping("/generateLabel")
    public ResponseEntity<?> generateLabelList(@RequestBody BoxRequest boxRequest){
        return warehouseBoxLabelsService.generateLabelsLists(boxRequest);
    }

    @GetMapping("/getLabelInfo/{labelId}")
    public ResponseEntity<?> getOrderInfoByBoxLabel(@PathVariable("labelId") String labelId){
        try{
            return warehouseBoxLabelsService.getByBoxLabel(labelId);
        }catch (NotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND) ;
        }
    }
}
