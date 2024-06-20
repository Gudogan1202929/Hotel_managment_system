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

    @NotNull(message = "Room class ID is required")
    private Long roomClassId;

    @NotNull(message = "Status ID is required")
    private Long statusId;

    @NotNull(message = "Room number is required")
    private int roomNumber;
}