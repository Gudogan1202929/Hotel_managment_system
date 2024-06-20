package com.example.HotelManagementSystem.repository;

import com.example.HotelManagementSystem.entity.CancellationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CancellationRequestRepo extends JpaRepository<CancellationRequest, Long>, JpaSpecificationExecutor<CancellationRequest> {
    boolean existsByReservationId(Long reservationId);
}
