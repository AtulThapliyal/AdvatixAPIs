package com.employee.advatixAPI.dto.productsDto;

import com.employee.advatixAPI.entity.Product.Product;
import com.employee.advatixAPI.entity.Product.ProductAttribute;
import lombok.Data;

import java.util.List;

@Data
public class ProductRequestDTO {
    Product product;
    List<ProductAttribute> productAttributes;
}
