package com.employee.advatixAPI.dto.warehouse;

import com.employee.advatixAPI.entity.warehouse.enums.BoxType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoxRequest {
    private  Integer quantity;
    private BoxType boxType;
    private Integer warehouseId;
}
