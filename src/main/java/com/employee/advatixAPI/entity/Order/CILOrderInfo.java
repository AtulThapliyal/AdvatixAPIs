package com.employee.advatixAPI.entity.Order;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Table(name = "oms_order_info")
@Entity
@Data
public class CILOrderInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    private Integer clientId;

    private String reason;

    private String shipToName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id_fk", referencedColumnName = "order_id")
    private List<CILOrderItems> orderItemsList;

    private Integer statusId;

    //address fields
    private String shipToAddress;
    private Integer shipToCountryId;
    private Integer shipToStateId;
    private Integer shipToCityId;

    private Integer carrierId;
    private String carrierName;
    private String serviceType;

    private String orderNumber;

    private Integer warehouseId;

}
