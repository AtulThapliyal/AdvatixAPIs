package com.employee.advatixAPI.dto.order;

import com.employee.advatixAPI.entity.Order.CILOrderItems;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;
@Data
public class OrderRequestDto {
    private Integer clientId;
    private List<OrderListRequestDto> orderItemsList;
    private Integer countryId;
    private Integer stateId;
    private Integer cityId;
}
