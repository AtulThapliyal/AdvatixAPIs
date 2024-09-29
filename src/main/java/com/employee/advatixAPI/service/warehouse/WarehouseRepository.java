package com.employee.advatixAPI.service.warehouse;

import com.employee.advatixAPI.entity.warehouse.Warehouse;
import com.employee.advatixAPI.entity.warehouse.WarehouseReceivedItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends JpaRepository<WarehouseReceivedItems, Integer>
{
}
