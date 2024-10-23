package com.employee.advatixAPI.dto.warehouse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipFromAddress {
    private String shipFromName;
    private String shipFromAddress;
    private String city;
    private String state;
    private String country;
    private String phoneNumber;
    private String emailId;
    private Boolean isResidential;
    private String postalCode;
}
