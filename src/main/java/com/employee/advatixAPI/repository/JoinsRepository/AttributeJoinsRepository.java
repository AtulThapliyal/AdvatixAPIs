package com.employee.advatixAPI.repository.JoinsRepository;

import com.employee.advatixAPI.entity.joinsEntity.AttributeJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeJoinsRepository extends JpaRepository<AttributeJoin, Integer> {
}
