package com.employee.advatixAPI.dto.productsDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AttributeInProduct {
    private Integer attributeId;
    private String attributeDesc;
    private String attributeName;
}
