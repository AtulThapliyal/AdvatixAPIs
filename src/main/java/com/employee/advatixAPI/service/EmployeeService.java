package com.employee.advatixAPI.service;

import com.employee.advatixAPI.dto.*;
import com.employee.advatixAPI.entity.EmployeeEntity;
import com.employee.advatixAPI.entity.Permissions;
import com.employee.advatixAPI.entity.RolesEntity;
import com.employee.advatixAPI.repository.EmployeeRepository;
import com.employee.advatixAPI.repository.PermissionRepository;
import com.employee.advatixAPI.repository.RolesRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RolesRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;


    public List<EmployeeEntity> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public String addEmployee(EmployeeEntity employee) {
//
//        RolesEntity rs = employee.getRoles();
//        Permissions permissions=rs.getPermissions();
//        rs.setEmp(employee);
//        employee.setRoles(rs);
//        rs.setPermissions(permissions);
//        permissions.setRole(rs);
//        employeeRepository.save(employee);
//        return employee.getName();
        return "";
    }

    public String deleteEmployee(int employeeId) {
        if (employeeRepository.findById(employeeId).isPresent()) {
            employeeRepository.delete(employeeRepository.findById(employeeId).get());
        }
        ;
        return "Successfully deleted";
    }

    public LoginMessage loginEmployee(LoginDto loginCredential) {
        EmployeeEntity employee = employeeRepository.findByEmail(loginCredential.getEmail());
        if (employee != null) {
            String password = loginCredential.getPassword();
            String encodedPassword = employee.getPassword();

            if (password.equals(encodedPassword)) {
                Optional<EmployeeEntity> user = employeeRepository.findOneByEmailAndPassword(loginCredential.getEmail(), encodedPassword);
                RolesEntity role = roleRepository.getRoleByRoleId(user.get().getRoleId());

                List<Permissions> permissions  = permissionRepository.getPermissionByRoleId(role.getRoleId());
                if (user.isPresent() ) {
                    EmployeeResponse employeeResponse = new EmployeeResponse();
                    employeeResponse.setPermissions(permissions);
                    employeeResponse.setRoles(role);
                    employeeResponse.setEmployee(employee);
                    return new LoginMessage("Login Success", true, employeeResponse);
                } else {
                    return new LoginMessage("Login Failure", false, null);
                }
            }
            return new LoginMessage("Password Is Incorrect", false, null);
        }

        return new LoginMessage("User is not registered", false, null);
    }

    public String create(EmployeeEntity employee){

        employeeRepository.save(employee);
        return "";
    }

    public EmployeeResponse getEmployeeById(Integer id) {
        Optional<EmployeeEntity> emp = employeeRepository.findById(id);
        EmployeeResponse employeeResponse = new EmployeeResponse();
        if(emp.isPresent()){
            employeeResponse.setEmployee(emp.get());
//            employeeResponse.setRoles(emp.get().getRole());
//            employeeResponse.setPermissions(emp.get().getRole().getPermissions());

        }
        return  employeeResponse;
    }
}
