package com.example.HotelManagementSystem.user.repositorie;

import com.example.HotelManagementSystem.user.entity.LogFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogFileRepo extends JpaRepository<LogFile, Long> {
}
