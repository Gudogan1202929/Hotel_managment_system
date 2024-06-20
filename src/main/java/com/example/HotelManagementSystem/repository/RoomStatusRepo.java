package com.example.HotelManagementSystem.repository;

import com.example.HotelManagementSystem.entity.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomStatusRepo extends JpaRepository<RoomStatus, Long> {
}
