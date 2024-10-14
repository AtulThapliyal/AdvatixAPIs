package com.employee.advatixAPI.entity.warehouse;

import com.employee.advatixAPI.entity.warehouse.enums.BoxType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "wh_box_label")

public class WarehouseBox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer boxId;

    @Enumerated(EnumType.STRING)
    private BoxType boxType;

    private Boolean status;

    private Integer warehouseId;

    private String boxLabel;

}

