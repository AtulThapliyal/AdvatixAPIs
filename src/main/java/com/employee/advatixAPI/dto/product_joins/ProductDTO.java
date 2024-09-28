package com.employee.advatixAPI.dto.product_joins;

import com.employee.advatixAPI.entity.joinsEntity.ProductAttributesJoinsEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
public class ProductDTO {

    private String productName;

    private String productSku;

    private List<AttributeDTO> attributes;
}
