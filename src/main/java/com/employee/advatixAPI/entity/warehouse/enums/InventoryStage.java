package com.employee.advatixAPI.entity.warehouse.enums;

import lombok.Getter;

@Getter
public enum InventoryStage {

    ON_HAND(1, "OnHand"), AVAILABLE(2, "Available"), DAMAGED(3, "Damage"), INREVIEW(4, "Inreview"),
    ALLOCATED(5, "Allocated"), DELIVERED(6, "Delivered"),
    ICQA(8, "ICQA"), KITTED(9, "Kitted"),
    DISPOSED(10, "Disposed"),EXPIRED(12, "Expired"), RESTOCK(13, "Restock");

    private final Integer stageId;
    private final String bucketName;

    InventoryStage(Integer id, String bucketName) {
        this.stageId = id;
        this.bucketName = bucketName;
    }
}
