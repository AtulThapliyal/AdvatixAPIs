package com.employee.advatixAPI.entity.Order;

import com.employee.advatixAPI.entity.warehouse.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "wh_order_container_assignment")
@Data
public class OrderPickerInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderPickerId;

    private String orderNumber;

    private String pickerName;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate createdOn;

    private LocalDate updatedOn;

    private String containerId;

    private Integer productId;

    private Integer productQty;

    private String boxId;

}
