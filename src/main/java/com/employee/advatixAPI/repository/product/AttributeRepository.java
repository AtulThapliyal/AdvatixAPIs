package com.employee.advatixAPI.repository.product;

import com.employee.advatixAPI.entity.Product.Attributes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeRepository extends JpaRepository<Attributes, Integer> {
}
