package com.employee.advatixAPI.entity.Order;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "wh_order_items")
@Data
@NoArgsConstructor
public class FEPOrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemId;

    private Integer productId;

    public FEPOrderItems(Integer productId, Integer productQty) {
        this.productId = productId;
        this.productQty = productQty;
    }

    private Integer productQty;
}
