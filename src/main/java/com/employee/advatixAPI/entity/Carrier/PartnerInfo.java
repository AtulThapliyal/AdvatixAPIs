package com.employee.advatixAPI.entity.Carrier;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "partner_info")
public class PartnerInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer partnerId;

    private String partnerName;

    private String serviceType;

    private Boolean isDefault;
}
