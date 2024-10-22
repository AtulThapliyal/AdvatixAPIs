package com.employee.advatixAPI.entity.lpn;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@Data
public class OrderLpnInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderLpnId;
    private String lpnNumber;
    private String orderNumber;
}
