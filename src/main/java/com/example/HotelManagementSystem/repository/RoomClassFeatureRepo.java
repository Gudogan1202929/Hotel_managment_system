package com.example.HotelManagementSystem.repository;

import com.example.HotelManagementSystem.entity.RoomClassFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomClassFeatureRepo  extends JpaRepository<RoomClassFeature, Long> {

}
