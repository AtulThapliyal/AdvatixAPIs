package com.employee.advatixAPI.dto.product_joins;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.SecondaryRow;

@Data
@AllArgsConstructor
public class AttributeResponse {
    private Integer attributeId;
    private String attributeName;
    private String attributeDescription;
}
