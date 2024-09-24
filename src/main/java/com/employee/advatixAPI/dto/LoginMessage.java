package com.employee.advatixAPI.dto;

import com.employee.advatixAPI.entity.EmployeeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@Data
@AllArgsConstructor
public class LoginMessage {
    private String message;
    private Boolean status;
    private EmployeeResponse employeeResponse;
}
