package com.employee.advatixAPI.entity.warehouse;

import com.employee.advatixAPI.entity.warehouse.enums.InventoryStage;
import com.employee.advatixAPI.entity.warehouse.enums.ReceiveStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wh_received_item_logs")
public class WarehouseReceivedItemLogs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private Long warehouseId;
    private Long customerId;
    private String locationBarcode;

    @Enumerated(EnumType.STRING)
    private ReceiveStatus receiveStatus;

    @Enumerated(EnumType.STRING)
    private InventoryStage inventoryStage;

    private Long quantity;
    private String lotNumber;
    private Long userId;


    private LocalDateTime createdOn;
}