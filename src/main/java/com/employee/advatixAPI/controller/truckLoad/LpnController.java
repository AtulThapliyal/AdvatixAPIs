package com.employee.advatixAPI.controller.truckLoad;

import com.employee.advatixAPI.dto.Lpn.LicensePlateNumberRequest;
import com.employee.advatixAPI.dto.Lpn.LpnOrders;
import com.employee.advatixAPI.service.Lpn.LpnService.LpnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lpn")
public class LpnController {

    @Autowired
    LpnService lpnService;

    @PostMapping("/generate/orderLpn")
    public String createOrderLpn(@RequestBody LpnOrders lpnOrders){
        return lpnService.addOrderLpn(lpnOrders);
    }

    @PostMapping("/generateLpn")
    public String generateLpn(@RequestBody LicensePlateNumberRequest licensePlateNumberRequest){
        return lpnService.createLpnNumber(licensePlateNumberRequest);
    }


}
