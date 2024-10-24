package com.employee.advatixAPI.dto.truckLoad;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderShipmentRequest {
    private String number;

    private Integer roomId;

    private String type;
}
