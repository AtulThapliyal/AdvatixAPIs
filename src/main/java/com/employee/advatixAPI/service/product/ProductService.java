package com.employee.advatixAPI.service.product;

import com.employee.advatixAPI.dto.AttributeInProduct;
import com.employee.advatixAPI.dto.ProductRequestDTO;
import com.employee.advatixAPI.dto.ProductResponse;
import com.employee.advatixAPI.dto.product_joins.AttributeDTO;
import com.employee.advatixAPI.dto.product_joins.AttributeResponse;
import com.employee.advatixAPI.dto.product_joins.ProductDTO;
import com.employee.advatixAPI.dto.product_joins.ProductJoinsResponse;
import com.employee.advatixAPI.entity.Product.Attributes;
import com.employee.advatixAPI.entity.Product.Product;
import com.employee.advatixAPI.entity.Product.ProductAttribute;
import com.employee.advatixAPI.entity.joinsEntity.AttributeJoin;
import com.employee.advatixAPI.entity.joinsEntity.ProductAttributesJoinsEntity;
import com.employee.advatixAPI.entity.joinsEntity.ProductsJoinsEntity;
import com.employee.advatixAPI.exception.NotFoundException;
import com.employee.advatixAPI.repository.JoinsRepository.AttributeJoinsRepository;
import com.employee.advatixAPI.repository.JoinsRepository.ProductAttributesJoinRepository;
import com.employee.advatixAPI.repository.JoinsRepository.ProductJoinsRepository;
import com.employee.advatixAPI.repository.product.AttributeRepository;
import com.employee.advatixAPI.repository.product.ProductAttributeRepository;
import com.employee.advatixAPI.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductAttributeRepository productAttributeRepository;

    @Autowired
    AttributeRepository attributeRepository;


    @Autowired
    ProductJoinsRepository productJoinsRepository;

    @Autowired
    AttributeJoinsRepository attributeJoinsRepository;

    @Autowired
    ProductAttributesJoinRepository productAttributesJoinRepository;



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

    public ProductsJoinsEntity saveProductInJoins(ProductDTO product) {

        for (AttributeDTO attributes: product.getAttributes()){
            attributeJoinsRepository.findById(attributes.getAttributeId()).orElseThrow(()->{
                 return new NotFoundException("Attribute with id " + attributes.getAttributeId() + " not found");
            });
        }

        ProductsJoinsEntity productsJoins = new ProductsJoinsEntity();

        productsJoins.setProductName(product.getProductName());
        productsJoins.setProductSku(product.getProductSku());

        productsJoins.setProductAttributes(product.getAttributes().stream().map
        (attributeDTO ->
        new ProductAttributesJoinsEntity(attributeDTO.getAttributeDescription(), attributeJoinsRepository.findById(attributeDTO.getAttributeId()).get())
        ).collect(Collectors.toList()));
        return productsJoins;
    }

    public ProductJoinsResponse getProduct(Integer productId) {

        ProductsJoinsEntity productsJoins = productJoinsRepository.findById(productId).get();
        List<ProductAttributesJoinsEntity> productAttributes = productAttributesJoinRepository.findAllByProductId(productId);

        ProductJoinsResponse productDTO = new ProductJoinsResponse();

        List<AttributeResponse> attributesOfProduct = new ArrayList<>();
        productAttributes.forEach(productAttribute -> {
            attributesOfProduct.add(new AttributeResponse(productAttribute.getAttribute().getAttributeId(), productAttribute.getAttribute().getAttributeName(), productAttribute.getAttributeDescription()));
        });

        productDTO.setProductName(productsJoins.getProductName());
        productDTO.setProductSku(productsJoins.getProductSku());

        productDTO.setAttributes(attributesOfProduct);
        return productDTO;
    }
}
