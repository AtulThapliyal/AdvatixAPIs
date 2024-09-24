package com.employee.advatixAPI.controller;

import com.employee.advatixAPI.dto.*;
import com.employee.advatixAPI.entity.EmployeeEntity;
import com.employee.advatixAPI.service.EmployeeService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;


//    OLD with employee entity
//    @PostMapping("/addEmployee")
//    public String addEmployee(@RequestBody EmployeeEntity employee){
//        return employeeService.addEmployee(employee);
//    }

    @PostMapping("/login")
    public ResponseEntity<?> loginEmployee(@RequestBody LoginDto loginCredential){
        LoginMessage loginMessage = employeeService.loginEmployee(loginCredential);
        return ResponseEntity.ok(loginMessage);
    }


    @PostMapping("/addEmployee")
    public  String addEmployee(@RequestBody EmployeeEntity request){
        String result =  employeeService.create(request);
        return result;
    }

    @GetMapping("/getEmployee/{id}")
    public EmployeeResponse getEmployeeById(@PathVariable Integer id){
        return employeeService.getEmployeeById(id);
    }
}
