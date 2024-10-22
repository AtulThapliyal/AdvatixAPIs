package com.employee.advatixAPI.repository.Lpn;

import com.employee.advatixAPI.entity.lpn.LpnInfo;
import com.employee.advatixAPI.entity.lpn.LpnStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Repository
public interface LpnInfoRepository extends MongoRepository<LpnInfo, Long>{
    Optional<LpnInfo> findByLpnNumber(String lpnNumber);
}
