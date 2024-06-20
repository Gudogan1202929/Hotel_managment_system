package com.example.HotelManagementSystem.repository;

import com.example.HotelManagementSystem.entity.RoomClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomClassRepo extends JpaRepository<RoomClass, Long> {

}
