package com.employee.advatixAPI.entity.Client;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "country")
@Data

public class Country
{
    @Id
    private Integer countryId;

    private String countryName;
    private String countryCode;
}
