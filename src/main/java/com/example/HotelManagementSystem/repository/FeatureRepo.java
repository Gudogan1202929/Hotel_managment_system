package com.example.HotelManagementSystem.repository;


import com.example.HotelManagementSystem.entity.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureRepo extends JpaRepository<Feature, Long> {

}
