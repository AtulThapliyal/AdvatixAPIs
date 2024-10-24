package com.employee.advatixAPI.entity.warehouse;

import com.employee.advatixAPI.entity.warehouse.enums.InventoryStage;
import com.employee.advatixAPI.entity.warehouse.enums.ReceiveStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "wh_pick_list")
public class PickListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productOrderId;

    private String orderNumber;

    private String productId;

    private Integer clientId;

    private Integer warehouseId;

    private InventoryStage inventoryStage;

    private ReceiveStatus receiveStatus;

    private String fromLocationBarCode;

    private String toLocationBarCode;
}
