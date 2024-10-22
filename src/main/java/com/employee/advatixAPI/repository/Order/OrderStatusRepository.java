package com.employee.advatixAPI.repository.Order;

import com.employee.advatixAPI.entity.Order.FEPOrderStatus;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusRepository extends JpaRepository<FEPOrderStatus, Integer> {

}
