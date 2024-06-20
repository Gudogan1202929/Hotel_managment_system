package com.example.HotelManagementSystem.controller;


import com.example.HotelManagementSystem.dto.EmployeeDto;
import com.example.HotelManagementSystem.dto.response.APIResponse;
import com.example.HotelManagementSystem.service.EmployeeServiceInt;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/employees")
public class EmployeeController {

    private final EmployeeServiceInt employeeService;

    @Autowired
    public EmployeeController(EmployeeServiceInt employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllEmployees() {
        log.info("Request to get all employees");
        APIResponse<List<EmployeeDto>> response = employeeService.getAllEmployees();
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @PostMapping
    public ResponseEntity<Object> createEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        log.info("Request to create a new employee: {}", employeeDto);
        APIResponse<EmployeeDto> response = employeeService.createEmployee(employeeDto);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEmployeeById(@PathVariable Long id) {
        log.info("Request to get employee by id: {}", id);
        APIResponse<EmployeeDto> response = employeeService.getEmployeeById(id);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDto employeeDto) {
        log.info("Request to update employee by id: {}", id);
        APIResponse<EmployeeDto> response = employeeService.updateEmployee(id, employeeDto);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable Long id) {
        log.info("Request to delete employee by id: {}", id);
        APIResponse<EmployeeDto> response = employeeService.deleteEmployee(id);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }
}