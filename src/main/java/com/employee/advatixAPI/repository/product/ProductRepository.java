package com.employee.advatixAPI.repository.product;

import com.employee.advatixAPI.entity.Product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByProductId(Integer productId);


    List<Product> findAllByProductSkuOrClientIdOrCreatedBy(String sku, Integer clientId, Integer createdBy);
    List<Product> findAllByProductSkuAndClientId(String sku, Integer clientId);

    List<Product> findAllByProductSkuAndCreatedBy(String sku, Integer createdBy);

    List<Product> findAllByClientIdAndCreatedBy(Integer clientId, Integer createdBy);
}
