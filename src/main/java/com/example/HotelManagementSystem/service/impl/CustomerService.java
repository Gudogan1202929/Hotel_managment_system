package com.example.HotelManagementSystem.service.impl;

import com.example.HotelManagementSystem.dto.CustomerDto;
import com.example.HotelManagementSystem.dto.response.APIResponse;
import com.example.HotelManagementSystem.entity.Customer;
import com.example.HotelManagementSystem.exception.BadRequestException;
import com.example.HotelManagementSystem.exception.ResourceNotFoundException;
import com.example.HotelManagementSystem.repository.CustomerRepo;
import com.example.HotelManagementSystem.service.CustomerServiceInt;
import com.example.HotelManagementSystem.user.repositorie.UserRepo;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements CustomerServiceInt {

    private final CustomerRepo customerRepository;
    private final UserRepo userRepository;

    @Autowired
    public CustomerService(CustomerRepo customerRepository, UserRepo userRepository) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }

    public APIResponse<List<CustomerDto>> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();

        List<CustomerDto> customerDtos = customers
                .stream()
                .map(customer -> CustomerDto.builder()
                        .id(customer.getId())
                        .user(userRepository.findById(customer.getUser().getId()).get())
                        .firstName(customer.getFirstName())
                        .lastName(customer.getLastName())
                        .address(customer.getAddress())
                        .phone(customer.getPhone())
                        .build())
                .toList();


        return APIResponse.ok(customerDtos, "Customers fetched successfully");
    }

    public APIResponse<CustomerDto> getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id).orElse(null);

        if (customer == null) {
            throw new ResourceNotFoundException(Customer.class, "id", id.toString());
        }

        CustomerDto customerDto = CustomerDto.builder()
                .id(customer.getId())
                .user(userRepository.findById(customer.getUser().getId()).get())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .address(customer.getAddress())
                .phone(customer.getPhone())
                .build();

        return APIResponse.ok(customerDto, "Customer fetched successfully");
    }

    public APIResponse<CustomerDto> createCustomer(CustomerDto customerDto) {
        if (customerDto.getId() != null) {
            throw new BadRequestException("Id should be null when creating a new customer");
        }

        if (customerRepository.findByPhone(customerDto.getPhone()) != null) {
            throw new BadRequestException("Customer with phone number " + customerDto.getPhone() + " already exists");
        }


        Customer customer = Customer.builder()
                .user(userRepository.findById(customerDto.getId()).get())
                .firstName(customerDto.getFirstName())
                .lastName(customerDto.getLastName())
                .address(customerDto.getAddress())
                .phone(customerDto.getPhone())
                .build();

        Customer savedCustomer = customerRepository.save(customer);

        CustomerDto savedCustomerDto = CustomerDto.builder()
                .id(savedCustomer.getId())
                .user(userRepository.findById(savedCustomer.getUser().getId()).get())
                .firstName(savedCustomer.getFirstName())
                .lastName(savedCustomer.getLastName())
                .address(savedCustomer.getAddress())
                .phone(savedCustomer.getPhone())
                .build();

        return APIResponse.created(savedCustomerDto, "Customer created successfully");


    }

    public APIResponse<CustomerDto> updateCustomer(Long id, CustomerDto customerDto) {
        Customer customer = customerRepository.findById(id).orElse(null);

        if (customer == null) {
            throw new ResourceNotFoundException(Customer.class, "id", id.toString());
        }

        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setAddress(customerDto.getAddress());
        customer.setPhone(customerDto.getPhone());

        Customer updatedCustomer = customerRepository.save(customer);

        CustomerDto updatedCustomerDto = CustomerDto.builder()
                .id(updatedCustomer.getId())
                .user(userRepository.findById(updatedCustomer.getUser().getId()).get())
                .firstName(updatedCustomer.getFirstName())
                .lastName(updatedCustomer.getLastName())
                .address(updatedCustomer.getAddress())
                .phone(updatedCustomer.getPhone())
                .build();

        return APIResponse.ok(updatedCustomerDto, "Customer updated successfully");
    }

    public APIResponse<CustomerDto> deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElse(null);

        if (customer == null) {
            throw new ResourceNotFoundException(Customer.class, "id", id.toString());
        }

        customerRepository.delete(customer);

        CustomerDto deletedCustomerDto = CustomerDto.builder()
                .id(customer.getId())
                .user(userRepository.findById(customer.getUser().getId()).get())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .address(customer.getAddress())
                .phone(customer.getPhone())
                .build();

        return APIResponse.ok(deletedCustomerDto, "Customer deleted successfully");
    }

    public APIResponse<List<CustomerDto>> searchByParams(Specification<Customer> params) {
        List<Customer> customers = customerRepository.findAll(params);

        List<CustomerDto> customerDtos = customers
                .stream()
                .map(customer -> CustomerDto.builder()
                        .id(customer.getId())
                        .user(userRepository.findById(customer.getUser().getId()).get())
                        .firstName(customer.getFirstName())
                        .lastName(customer.getLastName())
                        .address(customer.getAddress())
                        .phone(customer.getPhone())
                        .build())
                .toList();

        return APIResponse.ok(customerDtos, "Customers fetched successfully");
    }




}
