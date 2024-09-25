package com.employee.advatixAPI.entity.Client;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name ="city")
public class City {
    @Id
    private Integer cityId;

    private String cityName;

    private String cityCode;
    private Integer stateId;
    private Integer countryId;
}
