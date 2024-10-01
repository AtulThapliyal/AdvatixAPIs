package com.employee.advatixAPI.repository.Order;

import com.employee.advatixAPI.entity.Order.FEPOrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FEPOrderRepository extends JpaRepository<FEPOrderInfo, Integer> {
}
