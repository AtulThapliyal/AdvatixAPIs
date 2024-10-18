package com.employee.advatixAPI.dto.ShipmentLabel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentResponseDto {
    private Long shipment_id;
    private Long rate_id;
    private String shipment_tracking_number;
    private String confirmation;
    private List<LabelDTO> labels;
}
