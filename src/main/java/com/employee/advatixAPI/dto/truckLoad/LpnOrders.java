package com.employee.advatixAPI.dto.truckLoad;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LpnOrders {
    private String lpnNumber;
    private List<String> ordersList;
}

