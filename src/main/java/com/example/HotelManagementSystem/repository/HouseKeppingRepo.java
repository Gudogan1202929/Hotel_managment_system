package com.example.HotelManagementSystem.repository;

import com.example.HotelManagementSystem.entity.Housekeeping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseKeppingRepo extends JpaRepository<Housekeeping, Long> , JpaSpecificationExecutor<Housekeeping> {
}
