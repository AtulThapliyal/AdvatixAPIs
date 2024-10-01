package com.employee.advatixAPI.service.warehouse;

import com.employee.advatixAPI.entity.warehouse.Warehouse;
import com.employee.advatixAPI.entity.warehouse.WarehouseReceivedItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseRepository extends JpaRepository<WarehouseReceivedItems, Integer>
{
    List<WarehouseReceivedItems> findByProductIdIn(List<Integer> productIds);

    WarehouseReceivedItems findByProductId(Integer productId);
}
