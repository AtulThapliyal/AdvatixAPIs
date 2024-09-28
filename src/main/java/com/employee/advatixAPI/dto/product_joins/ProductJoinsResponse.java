package com.employee.advatixAPI.dto.product_joins;


import lombok.Data;

import java.util.List;

@Data
public class ProductJoinsResponse {
    private String productName;

    private String productSku;

    private List<AttributeResponse> attributes;
}
