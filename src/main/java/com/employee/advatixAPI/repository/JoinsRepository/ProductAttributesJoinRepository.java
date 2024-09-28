package com.employee.advatixAPI.repository.JoinsRepository;

import com.employee.advatixAPI.entity.joinsEntity.ProductAttributesJoinsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductAttributesJoinRepository extends JpaRepository<ProductAttributesJoinsEntity, Integer> {
    List<ProductAttributesJoinsEntity> findAllByProductId(Integer productId);
}
