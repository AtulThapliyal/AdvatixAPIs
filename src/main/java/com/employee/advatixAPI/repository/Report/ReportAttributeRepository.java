package com.employee.advatixAPI.repository.Report;

import com.employee.advatixAPI.entity.Report.ReportAttributes;
import com.employee.advatixAPI.entity.Report.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportAttributeRepository extends JpaRepository<ReportAttributes, Integer> {
    List<ReportAttributes> findAllByReportId(Integer reportId);
}
