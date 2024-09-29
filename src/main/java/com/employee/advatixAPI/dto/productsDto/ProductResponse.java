package com.employee.advatixAPI.dto.productsDto;

import com.employee.advatixAPI.entity.Product.Product;
import lombok.Data;

import java.util.List;

@Data
public class ProductResponse {
    private Product product;
    private List<AttributeInProduct> productAttributes;
}
