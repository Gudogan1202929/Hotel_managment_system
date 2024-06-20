package com.example.HotelManagementSystem.controller;

import com.example.HotelManagementSystem.dto.CustomerDto;
import com.example.HotelManagementSystem.dto.response.APIResponse;
import com.example.HotelManagementSystem.entity.Customer;
import com.example.HotelManagementSystem.service.CustomerServiceInt;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/customers")
public class CustomerController {

    private final CustomerServiceInt customerService;

    @Autowired
    public CustomerController(CustomerServiceInt customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllCustomers() {
        log.info("Request to get all customers");
        APIResponse<List<CustomerDto>> response = customerService.getAllCustomers();
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @PostMapping
    public ResponseEntity<Object> createCustomer(@Valid @RequestBody CustomerDto customerDto) {
        log.info("Request to create a new customer: {}", customerDto);
        APIResponse<CustomerDto> response = customerService.createCustomer(customerDto);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCustomerById(@PathVariable Long id) {
        log.info("Request to get customer by id: {}", id);
        APIResponse<CustomerDto> response = customerService.getCustomerById(id);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerDto customerDto) {
        log.info("Request to update customer by id: {}", id);
        APIResponse<CustomerDto> response = customerService.updateCustomer(id, customerDto);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable Long id) {
        log.info("Request to delete customer by id: {}", id);
        APIResponse<CustomerDto> response = customerService.deleteCustomer(id);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }



    @GetMapping("/search")
    public ResponseEntity<Object> searchCustomer(@RequestParam(required = false) String firstName,
                                                 @RequestParam(required = false) String lastName,
                                                 @RequestParam(required = false) String phone,
                                                    @RequestParam(required = false) String address) {
        log.info("Request to search customer by parameters: firstName={}, lastName={}, phone={}, address={}", firstName, lastName, phone, address);

        Specification<Customer> spec = Specification.where(null);


        if (firstName != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("firstName"), firstName));
        }

        if (lastName != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("lastName"), lastName));
        }

        if (phone != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("phone"), phone));
        }

        if (address != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("address"), address));
        }


        APIResponse<List<CustomerDto>> response = customerService.searchByParams(spec);

        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }



}