package com.employee.advatixAPI.entity.lpn;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class LpnStatus {
    @Indexed(unique = true)
    private Integer stautsId;
    private String statusDesc;
}
