package com.example.HotelManagementSystem.service.impl;

import com.example.HotelManagementSystem.dto.ReservationDto;
import com.example.HotelManagementSystem.entity.Customer;
import com.example.HotelManagementSystem.entity.Reservation;
import com.example.HotelManagementSystem.entity.Room;
import com.example.HotelManagementSystem.exception.BadRequestException;
import com.example.HotelManagementSystem.exception.ResourceNotFoundException;
import com.example.HotelManagementSystem.repository.CustomerRepo;
import com.example.HotelManagementSystem.repository.RoomRepo;
import com.example.HotelManagementSystem.repository.RoomReservationRepo;
import com.example.HotelManagementSystem.service.ReservationServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HotelManagementSystem.dto.response.APIResponse;

import java.util.List;

@Service
public class ReservationService implements ReservationServiceInt {


    private final RoomReservationRepo reservationRepo;
    private final CustomerRepo customerRepo;
    private final RoomRepo roomRepo;



    @Autowired
    public ReservationService(RoomReservationRepo reservationRepo, CustomerRepo customerRepo, RoomRepo roomRepo) {
        this.reservationRepo = reservationRepo;
        this.customerRepo = customerRepo;
        this.roomRepo = roomRepo;
    }

    @Override
    public APIResponse<List<ReservationDto>> getAllReservations() {
        List<Reservation> reservationList = reservationRepo.findAll();

        List<ReservationDto> reservationDtoList = reservationList
                .stream()
                .map(reservation -> ReservationDto.builder()
                        .id(reservation.getId())
                        .roomId(reservation.getRoom().getId())
                        .customerId(reservation.getCustomer().getId())
                        .checkInDate(reservation.getCheckInDate())
                        .checkOutDate(reservation.getCheckOutDate())
                        .build())
                .toList();

        return APIResponse.ok(reservationDtoList, "Reservations fetched successfully");

    }

    @Override
    public APIResponse<ReservationDto> getReservationById(Long id) {
        Reservation reservation = reservationRepo.findById(id).orElse(null);

        if (reservation == null) {
            throw new ResourceNotFoundException(Reservation.class, "id", id.toString());
        }

        ReservationDto reservationDto = ReservationDto.builder()
                .id(reservation.getId())
                .roomId(reservation.getRoom().getId())
                .customerId(reservation.getCustomer().getId())
                .checkInDate(reservation.getCheckInDate())
                .checkOutDate(reservation.getCheckOutDate())
                .build();

        return APIResponse.ok(reservationDto, "Reservation fetched successfully");
    }

    @Override
    public APIResponse<ReservationDto> createReservation(ReservationDto reservationDto) {

        //id should be null when creating a new reservation
        if (reservationDto.getId() != null) {
            throw new IllegalArgumentException("Id should be null when creating a new reservation");
        }

        //check if the cusromer exists
        if (customerRepo.findById(reservationDto.getCustomerId()).isEmpty()) {
            throw new ResourceNotFoundException(Reservation.class, "customerId", reservationDto.getCustomerId().toString());
        }

        //check if the room exists
        if (roomRepo.findById(reservationDto.getRoomId()).isEmpty()) {
            throw new ResourceNotFoundException(Reservation.class, "roomId", reservationDto.getRoomId().toString());
        }

        //check if the room is already reserved in the given date range
        if (reservationRepo.existsByRoomIdAndCheckInDateLessThanEqualAndCheckOutDateGreaterThanEqual(reservationDto.getRoomId(), reservationDto.getCheckOutDate(), reservationDto.getCheckInDate())) {
            throw new BadRequestException("Room is already reserved in the given date range");
        }


        Customer customer = customerRepo.findById(reservationDto.getCustomerId()).get();
        Room room = roomRepo.findById(reservationDto.getRoomId()).get();

        Reservation reservation = Reservation.builder()
                .customer(customer)
                .room(room)
                .checkInDate(reservationDto.getCheckInDate())
                .checkOutDate(reservationDto.getCheckOutDate())
                .build();

        reservation = reservationRepo.save(reservation);

        ReservationDto savedReservationDto = ReservationDto.builder()
                .id(reservation.getId())
                .roomId(reservation.getRoom().getId())
                .customerId(reservation.getCustomer().getId())
                .checkInDate(reservation.getCheckInDate())
                .checkOutDate(reservation.getCheckOutDate())
                .build();

        return APIResponse.created(savedReservationDto, "Reservation created successfully");
    }

    @Override
    public APIResponse<ReservationDto> updateReservation(Long id, ReservationDto reservationDto) {

        //check if the reservation exists
        Reservation reservation = reservationRepo.findById(id).orElse(null);

        if (reservation == null) {
            throw new ResourceNotFoundException(Reservation.class, "id", id.toString());
        }

        //check if the cusromer exists
        if (customerRepo.findById(reservationDto.getCustomerId()).isEmpty()) {
            throw new ResourceNotFoundException(Reservation.class, "customerId", reservationDto.getCustomerId().toString());
        }

        //check if the room exists
        if (roomRepo.findById(reservation.getId()).isEmpty()) {
            throw new ResourceNotFoundException(Reservation.class, "roomId", reservationDto.getRoomId().toString());
        }

        //check if the room is already reserved in the given date range
        if (reservationRepo.existsByRoomIdAndCheckInDateLessThanEqualAndCheckOutDateGreaterThanEqual(reservationDto.getRoomId(), reservationDto.getCheckOutDate(), reservationDto.getCheckInDate())) {
            throw new BadRequestException("Room is already reserved in the given date range");
        }

        Customer customer = customerRepo.findById(reservation
                .getCustomer().getId()).get();
        Room room = roomRepo.findById(reservation.getRoom().getId()).get();

        reservation.setCustomer(customer);
        reservation.setRoom(room);

        reservation.setCheckInDate(reservationDto.getCheckInDate());
        reservation.setCheckOutDate(reservationDto.getCheckOutDate());

        reservation = reservationRepo.save(reservation);

        ReservationDto updatedReservationDto = ReservationDto.builder()
                .id(reservation.getId())
                .roomId(reservation.getRoom().getId())
                .customerId(reservation.getCustomer().getId())
                .checkInDate(reservation.getCheckInDate())
                .checkOutDate(reservation.getCheckOutDate())
                .build();

        return APIResponse.ok(updatedReservationDto, "Reservation updated successfully");

    }

    @Override
    public APIResponse<ReservationDto> deleteReservation(Long id) {
        Reservation reservation = reservationRepo.findById(id).orElse(null);

        if (reservation == null) {
            throw new ResourceNotFoundException(Reservation.class, "id", id.toString());
        }

        reservationRepo.delete(reservation);

        return APIResponse.ok(null, "Reservation deleted successfully");
    }


}
