package com.example.HotelManagementSystem.repository;

import com.example.HotelManagementSystem.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RoomReservationRepo extends JpaRepository<Reservation, Long> , JpaSpecificationExecutor<Reservation> {

    boolean existsByRoomIdAndExpectedArrivalTimeLessThanEqualAndExpectedLeavingTimeGreaterThanEqual(Long roomId, Date expectedLeavingTime, Date expectedArrivalTime);

    boolean existsByRoomIdAndExpectedArrivalTimeLessThanEqualAndExpectedLeavingTimeGreaterThanEqualAndIdNot(Long roomId, Date expectedLeavingTime, Date expectedArrivalTime, Long id);
}
