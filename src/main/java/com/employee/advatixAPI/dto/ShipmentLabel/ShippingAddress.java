package com.employee.advatixAPI.dto.ShipmentLabel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShippingAddress {
    private String name;
    private String company;
    private Boolean is_residential;
    private String street1;
    private String city;
    private String state;
    private String country;
    private String street2;
    private String postal_code;
    private String phone;
    private String email;
}
