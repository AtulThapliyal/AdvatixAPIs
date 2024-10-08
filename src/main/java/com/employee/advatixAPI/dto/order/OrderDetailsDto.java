package com.employee.advatixAPI.dto.order;

import com.employee.advatixAPI.entity.Order.FEPOrderItems;
import com.employee.advatixAPI.entity.Product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class OrderDetailsDto {
    private String orderNumber;
    private List<FEPOrderItems> productDetails;
}
