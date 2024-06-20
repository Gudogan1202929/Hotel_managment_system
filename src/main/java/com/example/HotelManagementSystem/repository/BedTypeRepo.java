package com.example.HotelManagementSystem.repository;

import com.example.HotelManagementSystem.entity.BedType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BedTypeRepo extends JpaRepository<BedType, Long> {


}
