package com.example.HotelManagementSystem.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class EmployeeDto {
    private Long id;

    @NotEmpty(message = "First name is required")
    private String firstName;

    @NotEmpty(message = "Last name is required")
    private String lastName;

    @NotEmpty(message = "Position is required")
    private String position;

    @NotEmpty(message = "Phone is required")
    private String phone;

    @NotEmpty(message = "Address is required")
    private String address;
}
