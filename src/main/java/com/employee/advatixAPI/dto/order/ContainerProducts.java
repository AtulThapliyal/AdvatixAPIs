package com.employee.advatixAPI.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContainerProducts {
    private Integer productId;
    private Integer quantity;
}
