package com.example.HotelManagementSystem.repository;

import com.example.HotelManagementSystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> , JpaSpecificationExecutor<Employee> {
    Employee findByPhone(String phone);
}
