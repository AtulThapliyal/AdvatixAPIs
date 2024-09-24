package com.employee.advatixAPI.repository;


import com.employee.advatixAPI.entity.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<RolesEntity, Integer> {
    RolesEntity getRoleByRoleId(Integer roleId);
}
