package com.employee.advatixAPI.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "permissions")
@Data
public class Permissions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer permissionId;

    private String permissionName;

    private Boolean permitRead;

    private  Boolean permitWrite;

    private Boolean permitEdit;

    private Boolean status;

    private Integer roleId;

}
