package com.employee.advatixAPI.entity.Carrier;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="client_carrier_info")
public class ClientCarrierInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer clientCarrierId;

    private Integer clientId;

    private Integer partnerId;


}
