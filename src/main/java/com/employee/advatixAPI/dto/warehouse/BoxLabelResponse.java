package com.employee.advatixAPI.dto.warehouse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoxLabelResponse {
    private String orderNumber;
    private ShipFromAddress shipFromAddress;
    private ShipToAddress shipToAddress;
    private String carrierType;
    private String serviceType;
}
