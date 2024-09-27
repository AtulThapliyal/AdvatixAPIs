package com.employee.advatixAPI.repository.JoinsRepository;

import com.employee.advatixAPI.entity.joinsEntity.ProductsJoinsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJoinsRepository extends JpaRepository<ProductsJoinsEntity, Integer> {
}
