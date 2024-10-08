package com.employee.advatixAPI.repository.Order;

import com.employee.advatixAPI.entity.Order.OrderPickerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderPickerInfoRepository extends JpaRepository<OrderPickerInfo, Integer> {

}
