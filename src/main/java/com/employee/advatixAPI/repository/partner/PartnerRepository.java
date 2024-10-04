package com.employee.advatixAPI.repository.partner;

import com.employee.advatixAPI.entity.Carrier.PartnerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepository extends JpaRepository<PartnerInfo, Integer> {
    PartnerInfo findByPartnerId(Integer partnerId);
}
