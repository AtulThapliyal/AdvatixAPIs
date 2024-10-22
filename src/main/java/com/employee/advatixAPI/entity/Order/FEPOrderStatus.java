package com.employee.advatixAPI.entity.Order;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "fep_order_status")
@Data
public class FEPOrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="status_id")
    private Integer statusId;

    private String statusDesc;

}
