package com.employee.advatixAPI.repository.Order;

import com.employee.advatixAPI.entity.Order.CILOrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CILOrderRepository extends JpaRepository<CILOrderInfo, Integer> {
}
