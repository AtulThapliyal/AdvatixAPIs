package com.employee.advatixAPI.controller.truckLoad;

import com.employee.advatixAPI.dto.truckLoad.LpnOrders;
import com.employee.advatixAPI.dto.truckLoad.ManifestRequest;
import com.employee.advatixAPI.service.Lpn.LpnService.LpnService;
import com.employee.advatixAPI.service.truckLoad.ManifestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mainfest")
public class ManifestController {
    @Autowired
    ManifestService manifestService;

    @PostMapping("/generate/manifestDetail")
    public String createManifest(@RequestBody ManifestRequest manifestRequest){
        return manifestService.createManifest(manifestRequest);
    }
}
