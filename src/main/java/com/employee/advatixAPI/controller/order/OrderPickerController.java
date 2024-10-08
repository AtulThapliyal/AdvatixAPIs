package com.employee.advatixAPI.controller.order;

import com.employee.advatixAPI.dto.order.OrderPickerDto;
import com.employee.advatixAPI.service.order.OrderPickerInfoService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order/pick")
public class OrderPickerController {

    @Autowired
    OrderPickerInfoService orderPickerInfoService;

    @PostMapping("/assignPicker/{orderId}")
    public ResponseEntity<?> assignPicker(@RequestBody OrderPickerDto orderPickerDto){
        return orderPickerInfoService.assignPicker(orderPickerDto);
    }
}
