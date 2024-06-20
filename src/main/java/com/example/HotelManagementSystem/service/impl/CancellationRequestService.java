package com.example.HotelManagementSystem.service.impl;

import com.example.HotelManagementSystem.dto.CancellationRequestDto;
import com.example.HotelManagementSystem.dto.response.APIResponse;
import com.example.HotelManagementSystem.entity.CancellationRequest;
import com.example.HotelManagementSystem.exception.BadRequestException;
import com.example.HotelManagementSystem.exception.ResourceNotFoundException;
import com.example.HotelManagementSystem.repository.CancellationRequestRepo;
import com.example.HotelManagementSystem.repository.RoomReservationRepo;
import com.example.HotelManagementSystem.service.CancellationRequestServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CancellationRequestService implements CancellationRequestServiceInt {

    private final CancellationRequestRepo cancellationRequestRepo;
    private final RoomReservationRepo roomReservationRepo;


    @Autowired
    public CancellationRequestService(CancellationRequestRepo cancellationRequestRepo, RoomReservationRepo roomReservationRepo) {
        this.cancellationRequestRepo = cancellationRequestRepo;
        this.roomReservationRepo = roomReservationRepo;
    }

    @Override
    public APIResponse<List<CancellationRequestDto>> getAllCancellations() {
        List<CancellationRequest> cancellationRequests = cancellationRequestRepo.findAll();

        List<CancellationRequestDto> cancellationRequestDtos = cancellationRequests
                .stream()
                .map(cancellationRequest -> CancellationRequestDto.builder()
                        .id(cancellationRequest.getId())
                        .reservationId(cancellationRequest.getReservation().getId())
                        .status(cancellationRequest.getStatus())
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
                .status(cancellationRequest.getStatus())
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
                .status(CancellationRequest.Status.PENDING)
                .requestedAt(cancellationRequestDto.getRequestedAt())
                .build();

        cancellationRequest = cancellationRequestRepo.save(cancellationRequest);

        CancellationRequestDto newCancellationRequestDto = CancellationRequestDto.builder()
                .id(cancellationRequest.getId())
                .reservationId(cancellationRequest.getReservation().getId())
                .status(cancellationRequest.getStatus())
                .requestedAt(cancellationRequest.getRequestedAt())
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
        cancellationRequest.setStatus(cancellationRequestDto.getStatus());

        cancellationRequest = cancellationRequestRepo.save(cancellationRequest);

        CancellationRequestDto updatedCancellationRequestDto = CancellationRequestDto.builder()
                .id(cancellationRequest.getId())
                .reservationId(cancellationRequest.getReservation().getId())
                .status(cancellationRequest.getStatus())
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
                .status(cancellationRequest.getStatus())
                .requestedAt(cancellationRequest.getRequestedAt())
                .build();

        return APIResponse.ok(cancellationRequestDto, "Cancellation request deleted successfully");
    }




}
