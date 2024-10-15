package com.employee.advatixAPI.repository.Warehouse;

import com.employee.advatixAPI.entity.warehouse.WarehouseAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseAddressRepository extends JpaRepository<WarehouseAddressEntity, Integer>
{
}
