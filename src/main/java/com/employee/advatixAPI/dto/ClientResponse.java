package com.employee.advatixAPI.dto;

import com.employee.advatixAPI.entity.Client.City;
import com.employee.advatixAPI.entity.Client.ClientInfo;
import com.employee.advatixAPI.entity.Client.Country;
import com.employee.advatixAPI.entity.Client.States;
import com.employee.advatixAPI.entity.EmployeeEntity;
import com.employee.advatixAPI.entity.Permissions;
import com.employee.advatixAPI.entity.RolesEntity;
import lombok.Data;

import java.util.List;

@Data
public class ClientResponse {
    private ClientInfo client;
    private Country country;
    private States states;
    private City city;
}