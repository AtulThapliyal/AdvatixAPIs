package com.employee.advatixAPI.entity.Report;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "reports")
public class ReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reportId;

    private String reportName;

    private Boolean status;

    private LocalDate createdOn;

    private Integer createdBy;
}
