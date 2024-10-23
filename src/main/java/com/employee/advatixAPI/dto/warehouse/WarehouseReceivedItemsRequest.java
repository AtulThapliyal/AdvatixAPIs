package com.employee.advatixAPI.dto.warehouse;

import com.employee.advatixAPI.dto.order.Containers;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseReceivedItemsRequest {
    private Integer clientId;
    private Integer warehouseId;
    private Integer employeeId;
    private List<Containers> containerProductsList;
}
