package com.employee.advatixAPI.repository.Lpn;

import com.employee.advatixAPI.dto.truckLoad.LpnOrders;
import com.employee.advatixAPI.entity.lpn.OrderLpnInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LpnOrderRespository extends MongoRepository<OrderLpnInfo, Long> {
    Optional<List<OrderLpnInfo>> findAllByLpnNumber(String number);
}
