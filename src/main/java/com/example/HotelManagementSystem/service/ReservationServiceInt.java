package com.example.HotelManagementSystem.service;

import com.example.HotelManagementSystem.dto.ReservationDto;
import com.example.HotelManagementSystem.dto.response.APIResponse;
import com.example.HotelManagementSystem.entity.Reservation;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public interface ReservationServiceInt {

    APIResponse<List<ReservationDto>> getAllReservations();

    APIResponse<ReservationDto> getReservationById(Long id);

    APIResponse<ReservationDto> createReservation(ReservationDto reservationDto);

    APIResponse<ReservationDto> updateReservation(Long id, ReservationDto reservationDto);

    APIResponse<ReservationDto> deleteReservation(Long id);

    APIResponse<ReservationDto> checkIn(Long reservationId);

    APIResponse<ReservationDto> checkOut(Long roomId);

    APIResponse<ReservationDto> requestCancellation(Long reservationId);

    APIResponse<List<ReservationDto>> searchByParams(Specification<Reservation> params);

    }
