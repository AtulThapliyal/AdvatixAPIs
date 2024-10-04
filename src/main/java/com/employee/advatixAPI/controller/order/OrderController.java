package com.employee.advatixAPI.controller.order;

import com.employee.advatixAPI.dto.order.OrderRequestDto;
import com.employee.advatixAPI.entity.Order.CILOrderInfo;
import com.employee.advatixAPI.service.order.OrderService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;

//    @PostMapping("/generateOrder")
//    public ResponseEntity<?> createOrder(@RequestBody CILOrderInfo orderInfo){
//        return orderService.generateOrder(orderInfo);
//    }

    @PostMapping("/generateOrder")
    public ResponseEntity<?> generateOrder(@RequestBody OrderRequestDto orderRequestDto){
        return orderService.createOrder(orderRequestDto);
    }

}
