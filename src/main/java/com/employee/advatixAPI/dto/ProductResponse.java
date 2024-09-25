package com.employee.advatixAPI.dto;

import com.employee.advatixAPI.entity.Product.Product;
import com.employee.advatixAPI.entity.Product.ProductAttribute;
import lombok.Data;

import java.util.List;

@Data
public class ProductResponse {
    private Product product;
    private List<ProductAttribute> productAttributes;
}
