package com.employee.advatixAPI.entity.joinsEntity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "attribute_master_table")
@Data
public class AttributeJoin {

    @Id
    @Column(name  ="attribute_id")
    private Integer attributeId;

    private String attributeName;

    private Boolean status;

}
