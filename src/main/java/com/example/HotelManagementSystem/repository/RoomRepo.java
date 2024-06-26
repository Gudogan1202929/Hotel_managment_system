package com.example.HotelManagementSystem.repository;

import com.example.HotelManagementSystem.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepo extends JpaRepository<Room, Long> , JpaSpecificationExecutor<Room> {
    Room findByRoomNumber(int roomNumber);
    List<Room> findByRoomStatus(String status);
}
