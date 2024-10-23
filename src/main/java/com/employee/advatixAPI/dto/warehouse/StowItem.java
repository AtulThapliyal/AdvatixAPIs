package com.employee.advatixAPI.dto.warehouse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StowItem {
    private String containerId;
    private String binId;
    private Integer productId;
    private Integer quantity;
}
