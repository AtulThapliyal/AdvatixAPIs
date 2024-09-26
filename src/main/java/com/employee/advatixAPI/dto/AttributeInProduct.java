package com.employee.advatixAPI.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AttributeInProduct {
    private Integer attributeId;
    private String attributeDesc;
    private String attributeName;
}
