package com.employee.advatixAPI.repository.Warehouse;

import com.employee.advatixAPI.entity.warehouse.Warehouse;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WhRepository extends JpaRepository<Warehouse, Integer> {
}
