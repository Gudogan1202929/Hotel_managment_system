package com.example.HotelManagementSystem.repository;

import com.example.HotelManagementSystem.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> , JpaSpecificationExecutor<Customer> {
    Customer findByPhone(String phone);
    List<Customer> findByFirstNameContaining(String Firstname);
    List<Customer> findByLastNameContaining(String Lastname);
}
