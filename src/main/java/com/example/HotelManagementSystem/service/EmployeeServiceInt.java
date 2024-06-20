package com.example.HotelManagementSystem.service;

import com.example.HotelManagementSystem.dto.EmployeeDto;
import com.example.HotelManagementSystem.dto.response.APIResponse;
import com.example.HotelManagementSystem.entity.Employee;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeServiceInt {

    APIResponse<List<EmployeeDto>> getAllEmployees();

    APIResponse<EmployeeDto> getEmployeeById(Long id);

    APIResponse<EmployeeDto> createEmployee(EmployeeDto employeeDto);

    APIResponse<EmployeeDto> updateEmployee(Long id, EmployeeDto employeeDto);

    APIResponse<EmployeeDto> deleteEmployee(Long id);

    APIResponse<List<EmployeeDto>> searchByParams(Specification<Employee> params);

}
