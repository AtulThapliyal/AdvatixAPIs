package com.employee.advatixAPI.entity.warehouse;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "wh_address_info")
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseAddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer warehouseAddressId;

    private Integer warehouseId;

    private String address1;

    private String address2;

    private Integer countryId;

    private Integer cityId;

    private Integer stateId;

    private String phoneNumber;
    private String emailId;
    private Boolean isResidential;
    private String postalCode;

}
