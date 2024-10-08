package com.employee.advatixAPI.entity.Order;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "order_picker_info")
@Data
public class OrderPickerInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderPickerId;

    private String orderNumber;

    private String pickerName;

    private Boolean status;

    private LocalDate createdOn;

    private LocalDate updatedOn;
}
