package com.employee.advatixAPI.controller.report;

import com.employee.advatixAPI.service.report.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    ReportService reportService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getReportsById(@PathVariable Integer id){
        return reportService.getReportById(id);
    }
}
