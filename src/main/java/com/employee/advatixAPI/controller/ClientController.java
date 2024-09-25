package com.employee.advatixAPI.controller;

import com.employee.advatixAPI.dto.ClientResponse;
import com.employee.advatixAPI.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client")
public class ClientController
{

    @Autowired
    ClientService clientService;


    @GetMapping("/getClient/{clientId}")
    public ClientResponse getClientInfo(@PathVariable Integer clientId){
        return clientService.getClientById(clientId);
    }
}
