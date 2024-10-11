package com.employee.advatixAPI.entity.warehouse;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "wh_containers")
public class WarehouseContainers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer whContainerId;

    private String containerId;

    private String orderNumber;
}
