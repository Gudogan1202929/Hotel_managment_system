package com.example.HotelManagementSystem.user.repositorie;

import com.example.HotelManagementSystem.user.entity.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlackListRepo extends JpaRepository<BlackList, Long> {
    BlackList findByToken(String token);
}
