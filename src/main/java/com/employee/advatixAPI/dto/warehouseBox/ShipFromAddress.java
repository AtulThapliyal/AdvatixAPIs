package com.employee.advatixAPI.dto.warehouseBox;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipFromAddress {
    private String shipToName;
    private String shipToAddress;
    private String city;
    private String state;
    private String country;
}
