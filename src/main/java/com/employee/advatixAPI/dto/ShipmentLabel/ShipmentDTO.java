package com.employee.advatixAPI.dto.ShipmentLabel;

import com.employee.advatixAPI.dto.warehouseBox.ShipFromAddress;
import com.employee.advatixAPI.dto.warehouseBox.ShipToAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentDTO {
    private String external_reference;
    private ShippingAddress address_to;
    private ShippingAddress address_from;
    private List<ParcelDTO> parcels;
}
