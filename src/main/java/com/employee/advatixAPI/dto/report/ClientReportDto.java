package com.employee.advatixAPI.dto.report;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientReportDto {
    private Integer clientId;
    private String clientName;
}
