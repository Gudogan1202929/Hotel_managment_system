package com.example.HotelManagementSystem.controller;


import com.example.HotelManagementSystem.dto.EmployeeDto;
import com.example.HotelManagementSystem.dto.response.APIResponse;
import com.example.HotelManagementSystem.entity.Employee;
import com.example.HotelManagementSystem.service.EmployeeServiceInt;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Tag(name = "Employee", description = "All employee related endpoints (just for admin)")
@RequestMapping(value = "/api/v1/employees")
public class EmployeeController {

    private final EmployeeServiceInt employeeService;

    @Autowired
    public EmployeeController(EmployeeServiceInt employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(summary = "Retriving all employees", description = "This endpoint is used to get all employees")
    @GetMapping
    public ResponseEntity<Object> getAllEmployees() {
        log.info("Request to get all employees");
        APIResponse<List<EmployeeDto>> response = employeeService.getAllEmployees();
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @Operation(summary = "Create new employee", description = "This endpoint used for adding new employee")
    @PostMapping
    public ResponseEntity<Object> createEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        log.info("Request to create a new employee: {}", employeeDto);
        APIResponse<EmployeeDto> response = employeeService.createEmployee(employeeDto);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @Operation(summary = "Get employee by id", description = "This endpoint is used to get employee by id")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getEmployeeById(@PathVariable Long id) {
        log.info("Request to get employee by id: {}", id);
        APIResponse<EmployeeDto> response = employeeService.getEmployeeById(id);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @Operation(summary = "Update employee by id", description = "This endpoint is used to update employee by id")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDto employeeDto) {
        log.info("Request to update employee by id: {}", id);
        APIResponse<EmployeeDto> response = employeeService.updateEmployee(id, employeeDto);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @Operation(summary = "Delete employee by id", description = "This endpoint is used to delete employee by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable Long id) {
        log.info("Request to delete employee by id: {}", id);
        APIResponse<EmployeeDto> response = employeeService.deleteEmployee(id);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @Operation(summary = "Search employee by parameters", description = "This endpoint is used to search employee by parameters")
    @GetMapping("/search")
    public ResponseEntity<Object> searchEmployee(@RequestParam(required = false) String firstName,
                                                 @RequestParam(required = false) String lastName,
                                                 @RequestParam(required = false) String position,
                                                 @RequestParam(required = false) String phone,
                                                    @RequestParam(required = false) String address){
        log.info("Request to search employee by parameters: firstName={}, lastName={}, position={}, phone={}, address={}", firstName, lastName, position, phone, address);

        Specification<Employee> spec = Specification.where(null);


        if (firstName != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("firstName"), firstName));
        }

        if (lastName != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("lastName"), lastName));
        }

        if (position != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("position"), position));
        }

        if (phone != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("phone"), phone));
        }

        if (address != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("address"), address));
        }


        APIResponse<List<EmployeeDto>> response = employeeService.searchByParams(spec);

        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }
}