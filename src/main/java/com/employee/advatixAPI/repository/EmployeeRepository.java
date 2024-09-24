package com.employee.advatixAPI.repository;

import com.employee.advatixAPI.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {

    Optional<EmployeeEntity> findOneByEmailAndPassword(String email, String password);

    EmployeeEntity findByEmail(String email);
}
