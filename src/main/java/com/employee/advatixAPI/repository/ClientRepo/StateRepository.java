package com.employee.advatixAPI.repository.ClientRepo;

import com.employee.advatixAPI.entity.Address.States;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<States, Integer> {

    States getStatesByStateId(Integer stateId);

    States getStatesByStateIdAndCountryId(Integer stateId, Integer countryId);
}
