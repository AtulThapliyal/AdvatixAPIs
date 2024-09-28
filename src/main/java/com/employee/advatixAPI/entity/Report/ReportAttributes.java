package com.employee.advatixAPI.entity.Report;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "report_attributes")
public class ReportAttributes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer attributeId;

    private String attributeDescription;

    private Integer reportId;

}
