package com.employee.advatixAPI.repository.Warehouse;

import com.employee.advatixAPI.entity.warehouse.WarehouseBox;
import com.employee.advatixAPI.entity.warehouse.enums.BoxType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseBoxLabelRepository extends JpaRepository<WarehouseBox, Integer> {

    @Query("SELECT w FROM WarehouseBox w WHERE w.warehouseId = :warehouseId AND w.boxType = :boxType AND w.status = :boxStatus")
    List<WarehouseBox> findByBoxTypeAndWarehouseIdAndStatus(BoxType boxType, Integer warehouseId, Boolean boxStatus, Pageable pageable);
}
