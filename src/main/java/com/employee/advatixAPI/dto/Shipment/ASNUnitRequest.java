package com.employee.advatixAPI.dto.Shipment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ASNUnitRequest {
    private Integer quantity;
    private Integer receivedQuantity;
    private String location;
    private Integer productId;
}


