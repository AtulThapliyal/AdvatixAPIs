package com.employee.advatixAPI.entity.Order;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "wh_order_items")
@Data

public class FEPOrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemId;

    private Integer productId;

    private Integer productQty;
}
