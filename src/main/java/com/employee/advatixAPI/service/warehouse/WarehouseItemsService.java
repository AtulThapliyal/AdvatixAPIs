package com.employee.advatixAPI.service.warehouse;

import com.employee.advatixAPI.dto.warehouse.Stow;
import com.employee.advatixAPI.dto.warehouse.WarehouseReceivedItemsRequest;
import com.employee.advatixAPI.entity.warehouse.WarehouseReceivedItems;
import com.employee.advatixAPI.entity.warehouse.enums.InventoryStage;
import com.employee.advatixAPI.entity.warehouse.enums.ReceiveStatus;
import com.employee.advatixAPI.repository.Warehouse.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class WarehouseItemsService {

    @Autowired
    WarehouseRepository warehouseRepository;


    public ResponseEntity<?> receiveItemsInContainers(WarehouseReceivedItemsRequest warehouseItemsRequest) {
        saveItemsInWarehouse(warehouseItemsRequest, InventoryStage.ON_HAND);
        return ResponseEntity.ok("");
    }

    public ResponseEntity<?> stowItemsInBins(Stow stowRequest) {
        stowRequest.getStowItems().forEach(stowItem -> {
            Optional<WarehouseReceivedItems> whItems = warehouseRepository.findByWarehouseIdAndClientIdAndProductIdAndInventoryStageAndLocation(stowRequest.getWarehouseId(), stowRequest.getClientId(), stowItem.getProductId(), InventoryStage.AVAILABLE, stowItem.getBinId());

            Optional<WarehouseReceivedItems> itemInContainer = warehouseRepository.findByWarehouseIdAndClientIdAndProductIdAndInventoryStageAndLocation(stowRequest.getWarehouseId(), stowRequest.getClientId(), stowItem.getProductId(), InventoryStage.ON_HAND, stowItem.getContainerId());

            if(whItems.isPresent()){
                WarehouseReceivedItems receivedItems = whItems.get();
                receivedItems.setQuantity(receivedItems.getQuantity() + stowItem.getQuantity());
            }else{
                WarehouseReceivedItems warehouseReceivedItems = new WarehouseReceivedItems();

                warehouseReceivedItems.setWarehouseId(stowRequest.getWarehouseId());
                warehouseReceivedItems.setLocation(stowItem.getBinId());
                warehouseReceivedItems.setClientId(stowRequest.getClientId());
                warehouseReceivedItems.setQuantity(stowItem.getQuantity());
                warehouseReceivedItems.setProductId(stowItem.getProductId());
                warehouseReceivedItems.setCreatedOn(LocalDate.now());
                warehouseReceivedItems.setInventoryStage(InventoryStage.AVAILABLE);
                warehouseReceivedItems.setReceiveStatus(ReceiveStatus.STOW);
                warehouseReceivedItems.setEmployeeId(stowRequest.getEmployeeId());

                warehouseRepository.save(warehouseReceivedItems);
            }

            itemInContainer.get().setQuantity(itemInContainer.get().getQuantity() - stowItem.getQuantity());
            warehouseRepository.save(itemInContainer.get());
        });

        return ResponseEntity.ok("");
    }

    private void saveItemsInWarehouse(WarehouseReceivedItemsRequest warehouseItemsRequest, InventoryStage stage) {
        warehouseItemsRequest.getContainerProductsList().forEach(container -> {
            container.getProductsList().forEach(products -> {
                Optional<WarehouseReceivedItems> whItems = warehouseRepository.findByWarehouseIdAndClientIdAndProductIdAndInventoryStageAndLocation(warehouseItemsRequest.getWarehouseId(), warehouseItemsRequest.getClientId(), products.getProductId(), stage, container.getId());
                if (whItems.isPresent()) {
                    WarehouseReceivedItems receivedItems = whItems.get();
                    receivedItems.setQuantity(receivedItems.getQuantity() + products.getQuantity());
                } else {
                    WarehouseReceivedItems warehouseReceivedItems = new WarehouseReceivedItems();

                    warehouseReceivedItems.setWarehouseId(warehouseItemsRequest.getWarehouseId());
                    warehouseReceivedItems.setLocation(container.getId());
                    warehouseReceivedItems.setClientId(warehouseItemsRequest.getClientId());
                    warehouseReceivedItems.setQuantity(products.getQuantity());
                    warehouseReceivedItems.setProductId(products.getProductId());
                    warehouseReceivedItems.setCreatedOn(LocalDate.now());
                    warehouseReceivedItems.setInventoryStage(InventoryStage.ON_HAND);
                    warehouseReceivedItems.setReceiveStatus(ReceiveStatus.RECEIVED);

                    warehouseRepository.save(warehouseReceivedItems);
                }
            });
        });
    }
}
