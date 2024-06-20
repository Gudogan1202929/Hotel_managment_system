package com.example.HotelManagementSystem.dto;

import com.example.HotelManagementSystem.entity.Housekeeping;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class HousekeepingDto {
    private Long id;

    @NotNull(message = "Room ID is required")
    private Long roomId;

    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @NotNull(message = "Task date is required")
    private LocalDate taskDate;

    @NotNull(message = "Status is required")
    private Housekeeping.Status status;
}