package com.employee.advatixAPI.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigInteger;

@Entity
@Data
@Table(name = "employee")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "first_name")
    private String name;

    @Column(name = "last_name")
    private  String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    private BigInteger phoneNumber;

    private Boolean status;

    @Column(name = "role_Id")
    private Integer roleId;

//    @OneToOne
//    @JoinColumn(name = "role_Id" , referencedColumnName = "roleId")
//    private RolesEntity role;
}
