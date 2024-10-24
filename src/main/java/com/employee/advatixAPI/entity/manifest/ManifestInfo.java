package com.employee.advatixAPI.entity.manifest;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "mainfest_info")
public class ManifestInfo {
    @Id
    private Integer manifestId;
    private String manifestNumber;
    private String manifestTo;
    private String manifestFrom;
    private Integer status;
}
