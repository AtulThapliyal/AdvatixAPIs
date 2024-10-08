package com.employee.advatixAPI.dto.warehouseBox;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseBoxResponse {
    private List<Integer> boxIds;
}
