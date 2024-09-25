package com.employee.advatixAPI.controller.product;

import com.employee.advatixAPI.dto.ProductResponse;
import com.employee.advatixAPI.entity.Product.Product;
import com.employee.advatixAPI.service.product.ProductService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/getAllProducts")
    public List<Product> getProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/getProduct/{productId}")
    public ProductResponse getProductById(@PathVariable Integer productId){
        return productService.getProductById(productId);
    }
}
