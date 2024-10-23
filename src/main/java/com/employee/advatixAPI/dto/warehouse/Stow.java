package com.employee.advatixAPI.dto.warehouse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stow {
    private Integer clientId;
    private Integer warehouseId;
    private Integer employeeId;
    private List<StowItem> stowItems;
}
