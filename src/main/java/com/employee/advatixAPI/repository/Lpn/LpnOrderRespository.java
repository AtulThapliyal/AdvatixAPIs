package com.employee.advatixAPI.repository.Lpn;

import com.employee.advatixAPI.entity.lpn.OrderLpnInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LpnOrderRespository extends MongoRepository<OrderLpnInfo, Long> {
}
