package com.employee.advatixAPI.entity.manifest;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.scheduling.support.SimpleTriggerContext;

@Data
@Document(value = "manifest_mapping")
@AllArgsConstructor
@NoArgsConstructor
public class ManifestMapping {
    @Id
    private Integer manifestId;
    private String manifestNumber;
    private String orderNumber;
    private String lpnNumber;
}
