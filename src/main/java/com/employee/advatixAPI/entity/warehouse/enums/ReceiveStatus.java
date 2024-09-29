package com.employee.advatixAPI.entity.warehouse.enums;


import lombok.Getter;

@Getter
public enum ReceiveStatus {
    RECEIVED(1, "Received"), STOW(2, "Stow"), RELABEL(3, "Relabel"),
    PICKED(5, "PickedUp"), COMPLETE(6, "Complete");

    private final Integer receiveId;

    private final String fieldName;

    ReceiveStatus(Integer receiveId, String fieldName) {
        this.receiveId = receiveId;
        this.fieldName = fieldName;
    }
}
