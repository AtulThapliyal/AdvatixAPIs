package com.employee.advatixAPI.service.warehouse;

import com.employee.advatixAPI.dto.warehouseBox.BoxRequest;
import com.employee.advatixAPI.dto.warehouseBox.WarehouseBoxResponse;
import com.employee.advatixAPI.entity.warehouse.WarehouseBox;
import com.employee.advatixAPI.exception.NotFoundException;
import com.employee.advatixAPI.repository.Warehouse.WarehouseBoxLabelRepository;
import com.employee.advatixAPI.repository.Warehouse.WhRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WarehouseBoxLabelService {
    @Autowired
    WarehouseBoxLabelRepository warehouseBoxLabelRepository;

    @Autowired
    WhRepository whRepository;


    public ResponseEntity<?> generateLabelsLists(BoxRequest boxRequest) {
        Pageable pageable = PageRequest.of(0, boxRequest.getQuantity());

        if (!whRepository.existsById(boxRequest.getWarehouseId())) {
            throw new NotFoundException("The warehouse with warehouse id " + boxRequest.getWarehouseId() + " does not exists");
        }

        List<WarehouseBox> warehouseBoxList = warehouseBoxLabelRepository.findByBoxTypeAndWarehouseIdAndStatus(boxRequest.getBoxType(), boxRequest.getWarehouseId(), true, pageable);

        if (boxRequest.getQuantity() > warehouseBoxList.size()) {
            throw new NotFoundException("There are only " + warehouseBoxList.size() + " boxes present in warehouse");
        }

        WarehouseBoxResponse warehouseBoxResponse = new WarehouseBoxResponse();
        List<String> boxIds = new ArrayList<>();

        for (int i = 0; i < warehouseBoxList.size(); i++) {
            WarehouseBox warehouseBox = warehouseBoxList.get(i);
            warehouseBox.setStatus(false);


            boxIds.add(warehouseBox.getBoxLabel());
            warehouseBoxLabelRepository.save(warehouseBox);
        }

        warehouseBoxResponse.setBoxLabels(boxIds);

        return ResponseEntity.ok(warehouseBoxResponse);
    }
}
