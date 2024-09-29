package com.employee.advatixAPI.entity.warehouse;

import com.employee.advatixAPI.entity.warehouse.enums.InventoryStage;
import com.employee.advatixAPI.entity.warehouse.enums.ReceiveStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "wh_received_items")
@Data
public class WarehouseReceivedItems {
    @Id
    @GeneratedValue
    private Integer itemId;

    private Integer productId;
    private Integer warehouseId;
    private Integer clientId;
    private String location;

    @Enumerated(EnumType.STRING)
    private ReceiveStatus receiveStatus;

    @Enumerated(EnumType.STRING)
    private InventoryStage inventoryStage;

    private Integer quantity;
    private String lotNumber;
    private Integer employeeId;
    private LocalDate createdOn;
}
