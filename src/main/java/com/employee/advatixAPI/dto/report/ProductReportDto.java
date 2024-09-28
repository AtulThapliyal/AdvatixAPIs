package com.employee.advatixAPI.dto.report;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductReportDto {
    private Integer productId;
    private String productName;
    private BigInteger productUpc;
}

