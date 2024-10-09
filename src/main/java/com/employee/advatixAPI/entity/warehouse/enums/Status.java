package com.employee.advatixAPI.entity.warehouse.enums;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
public enum Status {
    PICKED(1, "Picked") , PACKED(2, "Packed");

    Status(Integer id, String statusName) {
        this.id = id;
        this.statusName = statusName;
    }

    private Integer id;
    private String statusName;

}
