package com.example.HotelManagementSystem.controller;


import com.example.HotelManagementSystem.dto.ReservationDto;
import com.example.HotelManagementSystem.dto.response.APIResponse;
import com.example.HotelManagementSystem.entity.Reservation;
import com.example.HotelManagementSystem.service.ReservationServiceInt;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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

    @PostMapping("/{id}/checkin")
    public ResponseEntity<Object> checkIn(@PathVariable Long id) {
        log.info("Request to check in reservation by id: {}", id);
        APIResponse<ReservationDto> response = reservationService.checkIn(id);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @PostMapping("/{id}/checkout")
    public ResponseEntity<Object> checkOut(@PathVariable Long id) {
        log.info("Request to check out reservation by id: {}", id);
        APIResponse<ReservationDto> response = reservationService.checkOut(id);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @PostMapping("{reservationId}/request-cancellation")
    public ResponseEntity<Object> requestCancellation(@PathVariable Long reservationId) {
        log.info("Request to cancel reservation by id: {}", reservationId);
        APIResponse<ReservationDto> response = reservationService.requestCancellation(reservationId);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }


    @GetMapping("/search")
    public ResponseEntity<Object> searchReservation(@RequestParam(required = false) Long customerId,
                                                   @RequestParam(required = false) Long roomId,
                                                   @RequestParam(required = false) String status,
                                                   @RequestParam(required = false) String checkInDate,
                                                   @RequestParam(required = false) String checkOutDate,
                                                    @RequestParam(required = false) String expectedArrivalTime,
                                                    @RequestParam(required = false) String expectedLeavingTime) {
        log.info("Request to search reservation by parameters: customerId={}, roomId={}, status={}, checkInDate={}, checkOutDate={}, expectedArrivalTime={}, expectedLeavingTime={}", customerId, roomId, status, checkInDate, checkOutDate, expectedArrivalTime, expectedLeavingTime);


        Specification<Reservation> spec = Specification.where(null);

        if (customerId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("customer").get("id"), customerId));
        }

        if (roomId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("room").get("id"), roomId));
        }

        if (status != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), status));
        }

        if (checkInDate != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("checkInDate"), checkInDate));
        }

        if (checkOutDate != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("checkOutDate"), checkOutDate));
        }

        if (expectedArrivalTime != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("expectedArrivalTime"), expectedArrivalTime));
        }

        if (expectedLeavingTime != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("expectedLeavingTime"), expectedLeavingTime));
        }


        APIResponse<List<ReservationDto>> response = reservationService.searchByParams(spec);

        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

}