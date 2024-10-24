package com.employee.advatixAPI.service.truckLoad;

import com.employee.advatixAPI.dto.truckLoad.ManifestRequest;
import com.employee.advatixAPI.entity.Order.FEPOrderInfo;
import com.employee.advatixAPI.entity.lpn.LpnInfo;
import com.employee.advatixAPI.entity.manifest.ManifestMapping;
import com.employee.advatixAPI.exception.NotFoundException;
import com.employee.advatixAPI.repository.Lpn.LpnInfoRepository;
import com.employee.advatixAPI.repository.Lpn.ManifestRepository;
import com.employee.advatixAPI.repository.Order.FEPOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ManifestService {

    @Autowired
    ManifestRepository manifestRepository;

    @Autowired
    LpnInfoRepository lpnInfoRepository;

    @Autowired
    FEPOrderRepository fepOrderRepository;

    public String createManifest(ManifestRequest manifestRequest) {
        ManifestMapping manifest = new ManifestMapping();

        manifestRequest.getLpnNumbers().forEach(lpn -> {
            Optional<LpnInfo> lpnInfo = lpnInfoRepository.findByLpnNumber(lpn);
            if (lpnInfo.isPresent()) {
                manifest.setManifestNumber(manifestRequest.getManifestNumber());
                manifest.setLpnNumber(lpn);
                manifestRepository.save(manifest);
            }
            throw new NotFoundException("Lpn not found");
        });

        manifestRequest.getOrderNumbers().forEach(order -> {
            Optional<FEPOrderInfo> orderInfo = fepOrderRepository.findByOrderNumber(order);
            if (orderInfo.isPresent()) {
                manifest.setManifestNumber(manifestRequest.getManifestNumber());
                manifest.setOrderNumber(order);
                manifestRepository.save(manifest);
            }
            throw new NotFoundException("Order not found");
        });
        return "Done";
    }
}
