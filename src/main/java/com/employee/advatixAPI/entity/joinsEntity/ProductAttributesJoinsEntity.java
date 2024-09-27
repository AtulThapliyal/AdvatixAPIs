package com.employee.advatixAPI.entity.joinsEntity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "attribute_join")
public class ProductAttributesJoinsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productAttributeId;

    private String attributeDescription;

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "attribute_id")
//    private Integer attribute;

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private AttributeJoin attribute;

    @Column(name = "product_id")
    private Integer productId;
}
