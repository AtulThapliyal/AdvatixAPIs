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

    @PostMapping("/assignPicker/container/{orderId}")
    public ResponseEntity<?> assignPickerContainer(@RequestBody OrderPickerDto orderPickerDto){
        return orderPickerInfoService.assignPickerContainer(orderPickerDto);
    }

    @PostMapping("/assign/box/{orderId}")
    public ResponseEntity<?> assignBoxOrder(@RequestBody OrderPickerDto orderPickerDto){
        return orderPickerInfoService.assignBoxOrder(orderPickerDto);
    }

    @PostMapping("/get/container")
    public String getContainer(){
        return orderPickerInfoService.getContainer();
    }
}
