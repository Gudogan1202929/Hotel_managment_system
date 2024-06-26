package com.example.HotelManagementSystem.service;

import com.example.HotelManagementSystem.dto.CustomerDto;
import com.example.HotelManagementSystem.dto.response.APIResponse;
import com.example.HotelManagementSystem.entity.Customer;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerServiceInt {

    APIResponse<List<CustomerDto>> getAllCustomers();

    APIResponse<CustomerDto> getCustomerById(Long id);

    APIResponse<CustomerDto> createCustomer(CustomerDto customerDto);

    APIResponse<CustomerDto> updateCustomer(Long id, CustomerDto customerDto);

    APIResponse<CustomerDto> deleteCustomer(Long id);

    APIResponse<List<CustomerDto>> searchByParams(Specification<Customer> params);

}
