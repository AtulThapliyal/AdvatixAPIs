package com.employee.advatixAPI.dto.report;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;

@Data
@AllArgsConstructor
public class EmployeeReportDto{
    private String name;
    private BigInteger phoneNumber;
}