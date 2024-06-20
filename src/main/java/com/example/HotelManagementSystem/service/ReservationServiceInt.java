package com.example.HotelManagementSystem.service;

import com.example.HotelManagementSystem.dto.ReservationDto;
import com.example.HotelManagementSystem.dto.response.APIResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReservationServiceInt {

    APIResponse<List<ReservationDto>> getAllReservations();

    APIResponse<ReservationDto> getReservationById(Long id);

    APIResponse<ReservationDto> createReservation(ReservationDto reservationDto);

    APIResponse<ReservationDto> updateReservation(Long id, ReservationDto reservationDto);

    APIResponse<ReservationDto> deleteReservation(Long id);



}
