package com.employee.advatixAPI.entity.joinsEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "attribute_join")
@NoArgsConstructor
@AllArgsConstructor
public class ProductAttributesJoinsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productAttributeId;

    private String attributeDescription;

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "attribute_id")

    public ProductAttributesJoinsEntity(String attributeDescription, AttributeJoin attribute) {
        this.attributeDescription = attributeDescription;
        this.attribute = attribute;
    }
//    private Integer attribute;

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private AttributeJoin attribute;

    @Column(name = "product_id")
    private Integer productId;
}
