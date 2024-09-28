package com.employee.advatixAPI.service.report;


import com.employee.advatixAPI.dto.report.ClientReportDto;
import com.employee.advatixAPI.dto.report.EmployeeReportDto;
import com.employee.advatixAPI.dto.report.ProductReportDto;
import com.employee.advatixAPI.entity.Client.ClientInfo;
import com.employee.advatixAPI.entity.EmployeeEntity;
import com.employee.advatixAPI.entity.Product.Product;
import com.employee.advatixAPI.entity.Report.ReportAttributes;
import com.employee.advatixAPI.entity.Report.ReportEntity;
import com.employee.advatixAPI.exception.NotFoundException;
import com.employee.advatixAPI.repository.ClientRepo.ClientRepository;
import com.employee.advatixAPI.repository.EmployeeRepository;
import com.employee.advatixAPI.repository.Report.ReportAttributeRepository;
import com.employee.advatixAPI.repository.Report.ReportRepository;
import com.employee.advatixAPI.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    @Autowired
    ReportAttributeRepository reportAttributeRepository;

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ClientRepository clientRepository;


    public ResponseEntity<?> getReportById(Integer reportId) {

        //get report name
        Optional<ReportEntity> report = reportRepository.findById(reportId);

        if (report.isPresent()) {
            String reportName = report.get().getReportName();

//        get list of attributes by reportId
            List<ReportAttributes> reportAttributes = reportAttributeRepository.findAllByReportId(reportId);

//        list employee
            if (reportName.equals("user")) {
                //make a list of dto for return
                List<EmployeeReportDto> employeeReport = new ArrayList<>();

                //find the list of employee in database
                List<EmployeeEntity> employees = employeeRepository.findAll();

                //setting the employee in dto
                employees.forEach(employeeReportDto -> {
                    employeeReport.add(new EmployeeReportDto(employeeReportDto.getName(), employeeReportDto.getPhoneNumber()));
                });

                 return ResponseEntity.ok(employeeReport);
            } else if (reportName.equals("product")) {
                //make a list of product dto
                List<ProductReportDto> productReport = new ArrayList<>();

                //find the list of product in database
                List<Product> product = productRepository.findAll();

                //setting the product in dto
                product.forEach(productReportDto -> {
                    productReport.add(new ProductReportDto(productReportDto.getProductId(), productReportDto.getProductName(), productReportDto.getProductUpc()));
                });

                return ResponseEntity.ok(productReport);
            }else if (reportName.equals("client")){
                //make a list of client dto
                List<ClientReportDto> productReport = new ArrayList<>();

                //find the list of client in database
                List<ClientInfo> product = clientRepository.findAll();

                //setting the product in dto
                product.forEach(clientReportDto -> {
                    productReport.add(new ClientReportDto(clientReportDto.getClientId(), clientReportDto.getClientName()));
                });

                return ResponseEntity.ok(productReport);
            }

        }
        return null;
    }


    public void findReportById(Integer productId){
        Optional<ReportEntity> report = reportRepository.findById(productId);



    }
}
