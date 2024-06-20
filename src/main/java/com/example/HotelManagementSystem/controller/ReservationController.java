package com.example.HotelManagementSystem.controller;


import com.example.HotelManagementSystem.dto.ReservationDto;
import com.example.HotelManagementSystem.dto.response.APIResponse;
import com.example.HotelManagementSystem.service.ReservationServiceInt;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/reservations")
public class ReservationController {

    private final ReservationServiceInt reservationService;

    @Autowired
    public ReservationController(ReservationServiceInt reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllReservations() {
        log.info("Request to get all reservations");
        APIResponse<List<ReservationDto>> response = reservationService.getAllReservations();
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @PostMapping
    public ResponseEntity<Object> createReservation(@Valid @RequestBody ReservationDto reservationDto) {
        log.info("Request to create a new reservation: {}", reservationDto);
        APIResponse<ReservationDto> response = reservationService.createReservation(reservationDto);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getReservationById(@PathVariable Long id) {
        log.info("Request to get reservation by id: {}", id);
        APIResponse<ReservationDto> response = reservationService.getReservationById(id);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateReservation(@PathVariable Long id, @Valid @RequestBody ReservationDto reservationDto) {
        log.info("Request to update reservation by id: {}", id);
        APIResponse<ReservationDto> response = reservationService.updateReservation(id, reservationDto);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteReservation(@PathVariable Long id) {
        log.info("Request to delete reservation by id: {}", id);
        APIResponse<ReservationDto> response = reservationService.deleteReservation(id);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }
}