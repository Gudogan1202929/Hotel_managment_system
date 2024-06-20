package com.example.HotelManagementSystem.dto;

import com.example.HotelManagementSystem.entity.CancellationRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CancellationRequestDto {
    private Long id;

    @NotNull(message = "Reservation ID is required")
    private Long reservationId;

    @NotNull(message = "Admin ID is required")
    private Long adminId;

    @NotNull(message = "Status is required")
    private CancellationRequest.Status status;

    @NotNull(message = "Requested at date is required")
    private LocalDateTime requestedAt;
}