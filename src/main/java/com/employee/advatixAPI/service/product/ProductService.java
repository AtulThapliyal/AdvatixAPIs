package com.employee.advatixAPI.service.product;

import com.employee.advatixAPI.dto.ProductResponse;
import com.employee.advatixAPI.entity.Product.Product;
import com.employee.advatixAPI.entity.Product.ProductAttribute;
import com.employee.advatixAPI.repository.product.ProductAttributeRepository;
import com.employee.advatixAPI.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductAttributeRepository productAttributeRepository;

    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }

    public ProductResponse getProductById(Integer productId) {
        ProductResponse productResponse = new ProductResponse();

        Optional<Product> product = productRepository.findByProductId(productId);

        if(product.isPresent()){
            productResponse.setProduct(product.get());
            List<ProductAttribute> productAttributes = productAttributeRepository.findAllByProductId(productId);
            productResponse.setProductAttributes(productAttributes);
            return productResponse;
        }
        return null;
    }
}
