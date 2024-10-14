package com.employee.advatixAPI.controller.warehouse;

import com.employee.advatixAPI.dto.warehouseBox.BoxRequest;
import com.employee.advatixAPI.service.warehouse.WarehouseBoxLabelService;
import org.springframework.beans.factory.annotation.Autowired;
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

//    @GetMapping("/getLabelInfo/{labelId}")
//    public ResponseEntity<?> getOrderInfoByBoxLabel(@PathVariable Integer labelId){
//
//    }
}
