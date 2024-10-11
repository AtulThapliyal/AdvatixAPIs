package com.employee.advatixAPI.dto.order;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPickerDto {
    private Integer orderId;
    private String pickerName;
    private List<Containers> pickLists;
}
