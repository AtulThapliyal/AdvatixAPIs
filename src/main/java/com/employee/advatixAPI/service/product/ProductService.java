package com.employee.advatixAPI.service.product;

import com.employee.advatixAPI.dto.AttributeInProduct;
import com.employee.advatixAPI.dto.ProductRequestDTO;
import com.employee.advatixAPI.dto.ProductResponse;
import com.employee.advatixAPI.entity.Product.Attributes;
import com.employee.advatixAPI.entity.Product.Product;
import com.employee.advatixAPI.entity.Product.ProductAttribute;
import com.employee.advatixAPI.repository.product.AttributeRepository;
import com.employee.advatixAPI.repository.product.ProductAttributeRepository;
import com.employee.advatixAPI.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service

public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductAttributeRepository productAttributeRepository;

    @Autowired
    AttributeRepository attributeRepository;

    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }

    public ProductResponse getProductById(Integer productId) {
        ProductResponse productResponse = new ProductResponse();

        Optional<Product> product = productRepository.findByProductId(productId);

        if(product.isPresent()){
            productResponse.setProduct(product.get());
            List<ProductAttribute> productAttributes = productAttributeRepository.findAllByProductId(productId);
            HashMap<Integer, String> attributeMap = new HashMap<>();
            List<Attributes> attributes = attributeRepository.findAll();

            List<AttributeInProduct> attributesOfProduct = new ArrayList<>();

            attributes.forEach(attribute -> {
                attributeMap.put(attribute.getAttributeId(), attribute.getAttributeDesc());
            });


            productAttributes.forEach(productAttribute -> {
                String attribute = attributeMap.get(productAttribute.getAttributeId());
                attributesOfProduct.add(new AttributeInProduct(productAttribute.getProductId(), productAttribute.getAttributeDesc(), attribute));
            });

            productResponse.setProductAttributes(attributesOfProduct);
            return productResponse;
        }
        return null;
    }

    public Product saveProductWithAttributes(ProductRequestDTO productRequestDTO) {

        System.out.println(productRequestDTO);
        Product savedProduct = productRepository.save(productRequestDTO.getProduct());
        for (ProductAttribute attributes : productRequestDTO.getProductAttributes()) {
            attributes.setProductId(savedProduct.getProductId());
            productAttributeRepository.save(attributes);
        }

        return  savedProduct;
    }

    public List<Product> getProductsByFilter(String sku, Integer clientId, Integer createdBy) {

        if(sku != null && clientId != null){
            return productRepository.findAllByProductSkuAndClientId(sku, clientId);
        }
        if(sku != null && createdBy != null){
            return productRepository.findAllByProductSkuAndCreatedBy(sku, createdBy);
        }
        if(clientId != null && createdBy != null){
            return productRepository.findAllByClientIdAndCreatedBy(clientId, createdBy);
        }
        return productRepository.findAllByProductSkuOrClientIdOrCreatedBy(sku, clientId, createdBy);
    }
}
