package com.employee.advatixAPI.dto.truckLoad;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetOrdersRTS {
    private String orderNumber;

    private Integer warehouseId;
    private Integer carrierId;
    private String carrierName;
    private String serviceType;

    private Integer statusId;
    private String status;

    private Long totalWeight;
    private Long totalQuantity;

    private String phone;
    private String email;
}
