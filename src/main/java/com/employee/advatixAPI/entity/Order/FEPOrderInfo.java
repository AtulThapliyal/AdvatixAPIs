package com.employee.advatixAPI.entity.Order;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Table(name = "wh_order_info")
@Entity
@Data
public class FEPOrderInfo {

    @Id
    @Column(name = "order_id")
    private Integer orderId;

    private Integer clientId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id_fk", referencedColumnName = "order_id")
    private List<FEPOrderItems> orderItemsList;

    //address
    private String shipToAddress;
    private String shipToName;
    private Integer shipToCountryId;
    private Integer shipToStateId;
    private Integer shipToCityId;

    private Integer carrierId;
    private String carrierName;
    private String serviceType;
    private Integer warehouseId;

    private Integer statusId;

    private String orderNumber;

    private Long totalWeight;
    private Long totalQuantity;

    private String postalCode;
    private String phone;
    private String email;
    private Boolean isResidential;

}

