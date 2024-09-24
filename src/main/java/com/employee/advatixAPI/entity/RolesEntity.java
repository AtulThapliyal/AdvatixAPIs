package com.employee.advatixAPI.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
@Data
public class RolesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    private String roleName;

    private String roleDescription;

    private Boolean roleStatus;
}
