package com.example.HotelManagementSystem.dto;

import com.example.HotelManagementSystem.entity.Reservation;
import com.example.HotelManagementSystem.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CustomerDto {

    private Long id;

    @JsonIgnore
    private User user;

    private String firstName;

    private String lastName;

    private String phone;

    private String address;
}