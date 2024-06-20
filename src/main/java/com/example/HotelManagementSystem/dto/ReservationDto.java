package com.example.HotelManagementSystem.dto;

import com.example.HotelManagementSystem.entity.Reservation;
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

    @NotNull(message = "Check-in date is required")
    private Date checkInDate;

    @NotNull(message = "Check-out date is required")
    private Date checkOutDate;

    @NotNull(message = "Status is required")
    private Reservation.Status status;
}