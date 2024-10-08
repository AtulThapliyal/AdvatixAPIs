package com.employee.advatixAPI.entity.warehouse.enums;

import lombok.Getter;

@Getter
public enum BoxType {
    SMALL(1, "Small"), MEDIUM(2, "Medium"), LARGE(3, "Large");

    BoxType(Integer typeId, String boxType) {
        this.typeId = typeId;
        this.boxType = boxType;
    }

    private final Integer typeId;
    private final String boxType;
}
