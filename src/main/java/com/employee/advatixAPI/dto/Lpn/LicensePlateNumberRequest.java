package com.employee.advatixAPI.dto.Lpn;

import jakarta.persistence.Access;
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
