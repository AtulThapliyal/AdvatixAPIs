package com.employee.advatixAPI.entity.Order;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "oms_order_items")
@Data
public class CILOrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemId;

    private Integer productId;

    private Integer productQty;
}
