package com.example.HotelManagementSystem.service.impl;

import com.example.HotelManagementSystem.dto.EmployeeDto;
import com.example.HotelManagementSystem.dto.response.APIResponse;
import com.example.HotelManagementSystem.entity.Employee;
import com.example.HotelManagementSystem.exception.BadRequestException;
import com.example.HotelManagementSystem.exception.ResourceNotFoundException;
import com.example.HotelManagementSystem.repository.EmployeeRepo;
import com.example.HotelManagementSystem.service.EmployeeServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService implements EmployeeServiceInt {

    private final EmployeeRepo employeeRepository;


    @Autowired
    public EmployeeService(EmployeeRepo employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public APIResponse<List<EmployeeDto>> getAllEmployees() {
        List<Employee> employeeDtos = employeeRepository.findAll();

        List<EmployeeDto> employeeDtoList = employeeDtos
                .stream()
                .map(employee -> EmployeeDto.builder()
                        .id(employee.getId())
                        .firstName(employee.getFirstName())
                        .lastName(employee.getLastName())
                        .position(employee.getPosition())
                        .phone(employee.getPhone())
                        .address(employee.getAddress())
                        .build())
                .toList();

        return APIResponse.ok(employeeDtoList, "Employees fetched successfully");

    }

    @Override
    public APIResponse<EmployeeDto> getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElse(null);

        if (employee == null) {
            throw new ResourceNotFoundException(Employee.class, "id", id.toString());
        }

        EmployeeDto employeeDto = EmployeeDto.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .position(employee.getPosition())
                .phone(employee.getPhone())
                .address(employee.getAddress())
                .build();

        return APIResponse.ok(employeeDto, "Employee fetched successfully");
    }

    @Override
    public APIResponse<EmployeeDto> createEmployee(EmployeeDto employeeDto) {
        //id should be null when creating a new employee
        if (employeeDto.getId() != null) {
            throw new BadRequestException("Id should be null when creating a new employee");
        }

        //check if the employee already exists by phone number
        if (employeeRepository.findByPhone(employeeDto.getPhone()) != null) {
            throw new BadRequestException("Employee with phone number " + employeeDto.getPhone() + " already exists");
        }


        Employee employee = Employee.builder()
                .firstName(employeeDto.getFirstName())
                .lastName(employeeDto.getLastName())
                .position(employeeDto.getPosition())
                .phone(employeeDto.getPhone())
                .address(employeeDto.getAddress())
                .build();

        employeeRepository.save(employee);

        return APIResponse.created(employeeDto, "Employee created successfully");
    }

    @Override
    public APIResponse<EmployeeDto> updateEmployee(Long id, EmployeeDto employeeDto) {
        Employee employee = employeeRepository.findById(id).orElse(null);

        if (employee == null) {
            throw new ResourceNotFoundException(Employee.class, "id", id.toString());
        }

        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setPosition(employeeDto.getPosition());
        employee.setPhone(employeeDto.getPhone());
        employee.setAddress(employeeDto.getAddress());

        employeeRepository.save(employee);

        return APIResponse.ok(employeeDto, "Employee updated successfully");
    }

    @Override
    public APIResponse<EmployeeDto> deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id).orElse(null);

        if (employee == null) {
            throw new ResourceNotFoundException(Employee.class, "id", id.toString());
        }

        employeeRepository.delete(employee);

        EmployeeDto employeeDto = EmployeeDto.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .position(employee.getPosition())
                .phone(employee.getPhone())
                .address(employee.getAddress())
                .build();

        return APIResponse.deleted(employeeDto, "Employee deleted successfully");
    }

    //public APIResponse<List<CustomerDto>> searchByParams(Specification<Customer> params) {
    //        List<Customer> customers = customerRepository.findAll(params);
    //
    //        List<CustomerDto> customerDtos = customers
    //                .stream()
    //                .map(customer -> CustomerDto.builder()
    //                        .id(customer.getId())
    //                        .user(userRepository.findById(customer.getUser().getId()).get())
    //                        .firstName(customer.getFirstName())
    //                        .lastName(customer.getLastName())
    //                        .address(customer.getAddress())
    //                        .phone(customer.getPhone())
    //                        .build())
    //                .toList();
    //
    //        return APIResponse.ok(customerDtos, "Customers fetched successfully");
    //    }

    @Override
    public APIResponse<List<EmployeeDto>> searchByParams(Specification<Employee> params) {
        List<Employee> employees = employeeRepository.findAll(params);

        List<EmployeeDto> employeeDtos = employees
                .stream()
                .map(employee -> EmployeeDto.builder()
                        .id(employee.getId())
                        .firstName(employee.getFirstName())
                        .lastName(employee.getLastName())
                        .position(employee.getPosition())
                        .phone(employee.getPhone())
                        .address(employee.getAddress())
                        .build())
                .toList();

        return APIResponse.ok(employeeDtos, "Employees fetched successfully");
    }



}
