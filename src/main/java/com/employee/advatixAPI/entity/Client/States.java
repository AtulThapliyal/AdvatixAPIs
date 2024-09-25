package com.employee.advatixAPI.entity.Client;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "states")
@Data
public class States {
    @Id
    private Integer stateId;

    private String stateName;
    private String stateCode;
    private Integer countryId;
}
