package com.example.HotelManagementSystem.repository;

import com.example.HotelManagementSystem.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {
    Customer findBy(String email);
    Customer findByPhone(String phone);
}
