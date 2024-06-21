package com.example.HotelManagementSystem.dto;

import com.example.HotelManagementSystem.entity.Reservation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ReservationDto {
    private Long id;

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Room ID is required")
    private Long roomId;

    private Date checkInDate;

    private Date checkOutDate;

    //expected arrival time
    @NotNull(message = "Expected arrival time is required")
    private Date expectedArrivalTime;

    //expected leaving time
    @NotNull(message = "Expected leaving time is required")
    private Date expectedLeavingTime;

    private LocalDateTime createdAt = LocalDateTime.now();
}