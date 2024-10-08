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
    private Integer countryId;
    private Integer stateId;
    private Integer cityId;

    private Integer carrierId;
    private String carrierName;
    private String serviceType;

    private Integer statusId;

    private String orderNumber;
}

