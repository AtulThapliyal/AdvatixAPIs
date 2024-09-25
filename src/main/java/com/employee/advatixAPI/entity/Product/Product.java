package com.employee.advatixAPI.entity.Product;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigInteger;
import java.sql.Date;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;
    private String productName;
    private String productDesc;
    private String productSku;
    private BigInteger productUpc;
    private String productCategory;
    private Boolean productStatus;
    private Integer clientId;
    private Integer createdBy;
    private Date createdOn;
}
