package com.example.HotelManagementSystem.service.impl;

import com.example.HotelManagementSystem.dto.CancellationRequestDto;
import com.example.HotelManagementSystem.dto.response.APIResponse;
import com.example.HotelManagementSystem.entity.CancellationRequest;
import com.example.HotelManagementSystem.entity.Reservation;
import com.example.HotelManagementSystem.entity.Room;
import com.example.HotelManagementSystem.exception.BadRequestException;
import com.example.HotelManagementSystem.exception.ResourceNotFoundException;
import com.example.HotelManagementSystem.repository.CancellationRequestRepo;
import com.example.HotelManagementSystem.repository.RoomRepo;
import com.example.HotelManagementSystem.repository.RoomReservationRepo;
import com.example.HotelManagementSystem.service.CancellationRequestServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CancellationRequestService implements CancellationRequestServiceInt {

    private final CancellationRequestRepo cancellationRequestRepo;
    private final RoomReservationRepo roomReservationRepo;
    private final RoomRepo roomRepo;


    @Autowired
    public CancellationRequestService(RoomRepo roomRepo , CancellationRequestRepo cancellationRequestRepo, RoomReservationRepo roomReservationRepo) {
        this.cancellationRequestRepo = cancellationRequestRepo;
        this.roomReservationRepo = roomReservationRepo;
        this.roomRepo = roomRepo;
    }

    @Override
    public APIResponse<List<CancellationRequestDto>> getAllCancellations() {
        List<CancellationRequest> cancellationRequests = cancellationRequestRepo.findAll();

        List<CancellationRequestDto> cancellationRequestDtos = cancellationRequests
                .stream()
                .map(cancellationRequest -> CancellationRequestDto.builder()
                        .id(cancellationRequest.getId())
                        .reservationId(cancellationRequest.getReservation().getId())
                        .requestedAt(cancellationRequest.getRequestedAt())
                        .requestedAt(cancellationRequest.getRequestedAt())
                        .build())
                .toList();

        return APIResponse.ok(cancellationRequestDtos, "Customers fetched successfully");
    }

    public APIResponse<CancellationRequestDto> getCancellationById(Long id) {


        CancellationRequest cancellationRequest = cancellationRequestRepo.findById(id).orElse(null);

        if (cancellationRequest == null) {
            throw new ResourceNotFoundException(CancellationRequest.class, "id", id.toString());
        }

        CancellationRequestDto cancellationRequestDto = CancellationRequestDto.builder()
                .id(cancellationRequest.getId())
                .reservationId(cancellationRequest.getReservation().getId())
                .requestedAt(cancellationRequest.getRequestedAt())
                .build();

        return APIResponse.ok(cancellationRequestDto, "Cancellation request fetched successfully");
    }


    public APIResponse<CancellationRequestDto> createCancellationRequest(CancellationRequestDto cancellationRequestDto) {

        //id should be null when creating a new customer
        if (cancellationRequestDto.getId() != null) {
            throw new BadRequestException("Id should be null when creating a new cancellation request");
        }

        //check if the reservation id is null
        if (cancellationRequestDto.getReservationId() == null) {
            throw new ResourceNotFoundException(CancellationRequest.class, "reservationId", "null");
        }

        //find the reservation by id
        if (!roomReservationRepo.existsById(cancellationRequestDto.getReservationId())) {
            throw new ResourceNotFoundException(CancellationRequest.class, "reservationId", cancellationRequestDto.getReservationId().toString());
        }

        //create a new cancellation request
        CancellationRequest cancellationRequest = CancellationRequest.builder()
                .reservation(roomReservationRepo.findById(cancellationRequestDto.getReservationId()).get())
                .requestedAt(cancellationRequestDto.getRequestedAt())
                .requestedAt(LocalDateTime.now())
                .build();

        cancellationRequest = cancellationRequestRepo.save(cancellationRequest);

        CancellationRequestDto newCancellationRequestDto = CancellationRequestDto.builder()
                .id(cancellationRequest.getId())
                .reservationId(cancellationRequest.getReservation().getId())
                .requestedAt(cancellationRequest.getRequestedAt())
                .requestedAt(LocalDateTime.now())
                .build();

        return APIResponse.created(newCancellationRequestDto, "Cancellation request created successfully");
    }

    public APIResponse<CancellationRequestDto> updateCancellationRequest(Long id, CancellationRequestDto cancellationRequestDto) {

        CancellationRequest cancellationRequest = cancellationRequestRepo.findById(id).orElse(null);

        if (cancellationRequest == null) {
            throw new ResourceNotFoundException(CancellationRequest.class, "id", id.toString());
        }

        //check if the reservation id is null
        if (cancellationRequestDto.getReservationId() == null) {
            throw new ResourceNotFoundException(CancellationRequest.class, "reservationId", "null");
        }

        //find the reservation by id
        if (!roomReservationRepo.existsById(cancellationRequestDto.getReservationId())) {
            throw new ResourceNotFoundException(CancellationRequest.class, "reservationId", cancellationRequestDto.getReservationId().toString());
        }

        //update the cancellation request
        cancellationRequest.setReservation(roomReservationRepo.findById(cancellationRequestDto.getReservationId()).get());

        cancellationRequest = cancellationRequestRepo.save(cancellationRequest);

        CancellationRequestDto updatedCancellationRequestDto = CancellationRequestDto.builder()
                .id(cancellationRequest.getId())
                .reservationId(cancellationRequest.getReservation().getId())
                .requestedAt(cancellationRequest.getRequestedAt())
                .build();

        return APIResponse.ok(updatedCancellationRequestDto, "Cancellation request updated successfully");
    }

    public APIResponse<CancellationRequestDto> deleteCancellationRequest(Long id) {

        CancellationRequest cancellationRequest = cancellationRequestRepo.findById(id).orElse(null);

        if (cancellationRequest == null) {
            throw new ResourceNotFoundException(CancellationRequest.class, "id", id.toString());
        }

        cancellationRequestRepo.delete(cancellationRequest);


        CancellationRequestDto cancellationRequestDto = CancellationRequestDto.builder()
                .id(cancellationRequest.getId())
                .reservationId(cancellationRequest.getReservation().getId())
                .requestedAt(cancellationRequest.getRequestedAt())
                .build();

        return APIResponse.ok(cancellationRequestDto, "Cancellation request deleted successfully");
    }


    @Override
    public APIResponse<CancellationRequestDto> approveCancellationRequest(Long requestId) {

        CancellationRequest cancellationRequest = cancellationRequestRepo.findById(requestId).orElse(null);

        if (cancellationRequest == null) {
            throw new ResourceNotFoundException(CancellationRequest.class, "id", requestId.toString());
        }

        // Retrieve the reservation to be cancelled
        Reservation reservation = cancellationRequest.getReservation();

        if (reservation == null) {
            throw new ResourceNotFoundException(Reservation.class, "id", cancellationRequest.getReservation().getId().toString());
        }

        // Update the room status to AVAILABLE
        Room room = reservation.getRoom();
        room.setRoomStatus("AVAILABLE");
        roomRepo.save(room);

        // Delete the reservation
        roomReservationRepo.delete(reservation);

        // Update the cancellation request status to APPROVED
        cancellationRequestRepo.delete(cancellationRequest);

        CancellationRequestDto cancellationRequestDto = CancellationRequestDto.builder()
                .id(cancellationRequest.getId())
                .reservationId(cancellationRequest.getReservation().getId())
                .requestedAt(cancellationRequest.getRequestedAt())
                .build();

        return APIResponse.ok(cancellationRequestDto, "Cancellation request approved successfully");
    }


    @Override
    public APIResponse<CancellationRequestDto> rejectCancellationRequest(Long requestId) {

        CancellationRequest cancellationRequest = cancellationRequestRepo.findById(requestId).orElse(null);

        if (cancellationRequest == null) {
            throw new ResourceNotFoundException(CancellationRequest.class, "id", requestId.toString());
        }

        cancellationRequestRepo.delete(cancellationRequest);

        CancellationRequestDto cancellationRequestDto = CancellationRequestDto.builder()
                .id(cancellationRequest.getId())
                .reservationId(cancellationRequest.getReservation().getId())
                .requestedAt(cancellationRequest.getRequestedAt())
                .build();

        return APIResponse.ok(cancellationRequestDto, "Cancellation request rejected successfully");
    }



    //searchByParams
    @Override
    public APIResponse<List<CancellationRequestDto>> searchByParams(Specification<CancellationRequest> params) {

        List<CancellationRequest> cancellationRequests = cancellationRequestRepo.findAll(params);


        List<CancellationRequestDto> cancellationRequestDtos = cancellationRequests
                .stream()
                .map(cancellationRequest -> CancellationRequestDto.builder()
                        .id(cancellationRequest.getId())
                        .reservationId(cancellationRequest.getReservation().getId())
                        .requestedAt(cancellationRequest.getRequestedAt())
                        .build())
                .toList();

        return APIResponse.ok(cancellationRequestDtos, "Cancellation requests fetched successfully");
    }

}
