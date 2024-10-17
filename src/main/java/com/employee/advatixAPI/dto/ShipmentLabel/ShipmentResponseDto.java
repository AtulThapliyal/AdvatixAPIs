package com.employee.advatixAPI.dto.ShipmentLabel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentResponseDto {
    private Long shipmentId;
    private Long rateId;
    private String shipmentTrackingNumber;
    private String confirmation;
    private List<LabelDTO> labels;
}
