package com.employee.advatixAPI.dto;

import com.employee.advatixAPI.entity.EmployeeEntity;
import com.employee.advatixAPI.entity.Permissions;
import com.employee.advatixAPI.entity.RolesEntity;
import lombok.Data;

import java.util.List;

@Data
public class EmployeeResponse {
    private EmployeeEntity employee;
    private RolesEntity roles;
    private List<Permissions> permissions;
}
