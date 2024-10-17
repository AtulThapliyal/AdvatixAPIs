package com.employee.advatixAPI.dto.ShipmentLabel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LabelDTO {
    private Long id;
    private String labelUrl;
    private Double cost;
    private String finalMileCarrier;
    private String trackingUrl;
    private String trackingNumber;
    private String labelFormat;
}
