package com.employee.advatixAPI.dto.Shipment;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ASNNoticeRequestDto {
    private String poNumber;
    private String lotNumber;
    private Integer totalQuantity;
    private LocalDate createdOn;
    private Integer createdBy;
    private Integer warehouseId;
    private List<ASNUnitRequest> asnUnitList;
}
