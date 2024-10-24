package com.employee.advatixAPI.dto.truckLoad;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LicensePlateNumberRequest {
    private Integer warehouseId;
    private Integer shipperId;
}
