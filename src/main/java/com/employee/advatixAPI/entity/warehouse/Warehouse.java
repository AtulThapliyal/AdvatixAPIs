package com.employee.advatixAPI.entity.warehouse;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "warehouse")
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer warehouseId;

    private String warehouseName;
    private String marketDescription;
}
