package com.employee.advatixAPI.dto;

import com.employee.advatixAPI.entity.Address.City;
import com.employee.advatixAPI.entity.Client.ClientInfo;
import com.employee.advatixAPI.entity.Address.Country;
import com.employee.advatixAPI.entity.Address.States;
import lombok.Data;

@Data
public class ClientResponse {
    private ClientInfo client;
    private Country country;
    private States states;
    private City city;
}
