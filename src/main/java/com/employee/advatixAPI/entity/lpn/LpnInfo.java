package com.employee.advatixAPI.entity.lpn;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class LpnInfo {
    @Id
    private Long lpnId;
    private String lpnNumber;
    private Integer warehouseId;
    private String shipToAddress;
    private String shipFromAddress;
    private Integer status;
}
