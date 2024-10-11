package com.employee.advatixAPI.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContainerProductListDto {
    private String containerId;
    private Integer productId;
    private Integer quantity;
}
