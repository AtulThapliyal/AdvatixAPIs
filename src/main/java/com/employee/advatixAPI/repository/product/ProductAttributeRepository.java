package com.employee.advatixAPI.repository.product;

import com.employee.advatixAPI.entity.Product.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Integer> {
    List<ProductAttribute> findAllByProductId(Integer productId);
}
