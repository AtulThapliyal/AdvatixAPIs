package com.employee.advatixAPI.dto.truckLoad;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManifestRequest {
    private String manifestNumber;
    private List<String> lpnNumbers;
    private List<String> orderNumbers;
}
