package com.employee.advatixAPI.entity.joinsEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "attribute_master_table")
public class AttributeJoin {

    @Id
    @Column(name  ="attribute_id")
    private Integer attributeId;

    private String attributeName;

    private Boolean status;

}
