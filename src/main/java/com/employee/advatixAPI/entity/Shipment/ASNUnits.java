package com.employee.advatixAPI.entity.Shipment;

import com.employee.advatixAPI.entity.Product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@Table(name = "asn_notice_units")
@AllArgsConstructor
public class ASNUnits {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer unitId;

    private Integer quantity;

    private Integer receivedQty;

    private String location;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public ASNUnits(Integer quantity, Integer receivedQty, String location, Product product) {
        this.quantity = quantity;
        this.receivedQty = receivedQty;
        this.location = location;
        this.product = product;
    }


}
