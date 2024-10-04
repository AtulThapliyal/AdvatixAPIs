package com.employee.advatixAPI.repository.partner;

import com.employee.advatixAPI.entity.Carrier.ClientCarrierInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientCarrier extends JpaRepository<ClientCarrierInfo, Integer> {
    Optional<ClientCarrierInfo> findByClientId(Integer clientId);
}
