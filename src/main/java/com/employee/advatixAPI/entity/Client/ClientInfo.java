package com.employee.advatixAPI.entity.Client;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigInteger;

@Entity
@Data
@Table(name = "client")
public class ClientInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer clientId;

    private String clientName;

    private String clientEmail;
    private BigInteger clientPhone;
    private String address;

    //address fields
    private Integer countryId;
    private Integer stateId;
    private Integer cityId;

    //zip code
    private String zipCode;

    private Integer employeeId;
}
