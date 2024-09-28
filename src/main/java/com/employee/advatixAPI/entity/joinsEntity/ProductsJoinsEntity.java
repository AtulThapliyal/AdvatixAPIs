package com.employee.advatixAPI.entity.joinsEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "products_joins")
@AllArgsConstructor
@NoArgsConstructor
public class ProductsJoinsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    private String productName;

    private String productSku;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private List<ProductAttributesJoinsEntity> productAttributes;
}
