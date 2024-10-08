package com.employee.advatixAPI.entity.Order;

import jakarta.persistence.*;

@Entity
@Table(name = "fep_order_status")
public class FEPOrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="status_id")
    private Integer statusId;

    private String statusDesc;

}
