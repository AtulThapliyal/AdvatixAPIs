package com.employee.advatixAPI.repository.Order;

import com.employee.advatixAPI.entity.Order.FEPOrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FEPOrderRepository extends JpaRepository<FEPOrderInfo, Integer> {
    Optional<FEPOrderInfo> findByOrderNumber(String orderNumber);

    List<FEPOrderInfo> findAllByStatusId(Integer i);
}
