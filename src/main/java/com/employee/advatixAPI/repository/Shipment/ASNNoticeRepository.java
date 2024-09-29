package com.employee.advatixAPI.repository.Shipment;

import com.employee.advatixAPI.entity.Shipment.ASNNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ASNNoticeRepository extends JpaRepository<ASNNotice, Integer> {
}
