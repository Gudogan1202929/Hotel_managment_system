package com.example.HotelManagementSystem.repository;

import com.example.HotelManagementSystem.entity.RoomClassBedType;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RoomClassBedTypeRepo extends JpaRepository<RoomClassBedType, Long> {

}
