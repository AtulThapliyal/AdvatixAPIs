package com.employee.advatixAPI.dto.ShipmentLabel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LabelDTO {
    private Long id;
    private String label_url;
    private Double cost;
    private String final_mile_carrier;
    private String tracking_url;
    private String tracking_number;
    private String label_format;
}
