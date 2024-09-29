package com.employee.advatixAPI.controller.product;

import com.employee.advatixAPI.dto.productsDto.ProductRequestDTO;
import com.employee.advatixAPI.dto.productsDto.ProductResponse;
import com.employee.advatixAPI.entity.Product.Product;
import com.employee.advatixAPI.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/addProduct")
    public String createProduct(@RequestBody ProductRequestDTO product){

        productService.saveProductWithAttributes(product);
        return "";
    }

    @GetMapping("/getProducts")
    public List<Product> getProductsByFilter( @RequestParam(required = false) String sku, @RequestParam(required = false) Integer clientId, @RequestParam(required = false) Integer createdBy){
        return productService.getProductsByFilter(sku, clientId, createdBy);
    }
}
