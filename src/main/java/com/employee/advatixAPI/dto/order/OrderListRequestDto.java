package com.employee.advatixAPI.dto.order;

import lombok.Data;

@Data
public class OrderListRequestDto {
    private Integer productId;

    private Integer productQty;
}
