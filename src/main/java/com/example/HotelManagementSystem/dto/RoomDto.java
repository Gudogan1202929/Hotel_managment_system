package com.example.HotelManagementSystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RoomDto {

    private Long id;

    @NotNull(message = "Floor number is required")
    private int floorNumber;

    @NotNull(message = "Bed number is required")
    private int bedNumber;

    @NotNull(message = "Room info is required")
    private String roomInfo;

    @NotNull(message = "Status is required")
    private String status;

    @NotNull(message = "Room number is required")
    private int roomNumber;
}