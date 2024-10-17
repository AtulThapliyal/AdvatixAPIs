package com.employee.advatixAPI.dto.ShipmentLabel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoxRequestDto {
    private String orderNumber;
    private String boxLabel;
    private double boxWeight;
    private BoxDimensionsDTO dimensions;
}
