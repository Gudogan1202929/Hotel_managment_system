package com.example.HotelManagementSystem.service.impl;

import com.example.HotelManagementSystem.dto.RoomDto;
import com.example.HotelManagementSystem.dto.response.APIResponse;
import com.example.HotelManagementSystem.entity.Room;
import com.example.HotelManagementSystem.exception.BadRequestException;
import com.example.HotelManagementSystem.exception.ResourceNotFoundException;
import com.example.HotelManagementSystem.repository.*;
import com.example.HotelManagementSystem.service.RoomServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService implements RoomServiceInt {

    private final RoomRepo roomRepository;

    @Autowired
    public RoomService(RoomRepo roomRepository ) {
        this.roomRepository = roomRepository;
    }

    @Override
    public APIResponse<List<RoomDto>> getAllRooms() {
        List<Room> roomList = roomRepository.findAll();

        List<RoomDto> roomDtoList = roomList
                .stream()
                .map(room -> RoomDto.builder()
                        .id(room.getId())
                        .roomNumber(room.getRoomNumber())
                        .bedNumber(room.getBedNumber())
                        .roomInfo(room.getRoomInfo())
                        .floorNumber(room.getFloorNumber())
                        .status(room.getRoomStatus())
                        .build())
                .toList();

        return APIResponse.ok(roomDtoList, "Rooms fetched successfully");

    }

    @Override
    public APIResponse<RoomDto> getRoomById(Long id) {
        Room room = roomRepository.findById(id).orElse(null);

        if (room == null) {
            throw new ResourceNotFoundException(Room.class, "id", id.toString());
        }

        RoomDto roomDto = RoomDto.builder()
                .id(room.getId())
                .roomNumber(room.getRoomNumber())
                .bedNumber(room.getBedNumber())
                .roomInfo(room.getRoomInfo())
                .floorNumber(room.getFloorNumber())
                .status(room.getRoomStatus())
                .build();

        return APIResponse.ok(roomDto, "Room fetched successfully");
    }

    @Override
    public APIResponse<RoomDto> createRoom(RoomDto roomDto) {

        //id should be null when creating a new room
        if (roomDto.getId() != null) {
            throw new BadRequestException("Id should be null when creating a new room");
        }

        //check if the room already exists by room number
        if (roomRepository.findByRoomNumber(roomDto.getRoomNumber()) != null) {
            throw new BadRequestException("Room with room number " + roomDto.getRoomNumber() + " already exists");
        }

        Room room = Room.builder()
                .roomNumber(roomDto.getRoomNumber())
                .bedNumber(roomDto.getBedNumber())
                .roomInfo(roomDto.getRoomInfo())
                .floorNumber(roomDto.getFloorNumber())
                .roomStatus(roomDto.getStatus())
                .build();

        roomRepository.save(room);

        return APIResponse.ok(roomDto, "Room created successfully");

    }

    @Override
    public APIResponse<RoomDto> updateRoom(Long id, RoomDto roomDto) {

            Room room = roomRepository.findById(id).orElse(null);

            if (room == null) {
                throw new ResourceNotFoundException(Room.class, "id", id.toString());
            }

            //check if the room already exists by room number
            if (roomRepository.findByRoomNumber(roomDto.getRoomNumber()) != null && !room.getRoomNumber().equals(roomDto.getRoomNumber())) {
                throw new BadRequestException("Room with room number " + roomDto.getRoomNumber() + " already exists");
            }

            room.setRoomNumber(roomDto.getRoomNumber());
            room.setBedNumber(roomDto.getBedNumber());
            room.setRoomInfo(roomDto.getRoomInfo());
            room.setFloorNumber(roomDto.getFloorNumber());
            room.setRoomStatus(roomDto.getStatus());

            roomRepository.save(room);

            return APIResponse.ok(roomDto, "Room updated successfully");
    }

    @Override
    public APIResponse<RoomDto> deleteRoom(Long id) {

        if (roomRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException(Room.class, "id", id.toString());
        }

        roomRepository.deleteById(id);

        return APIResponse.ok(null, "Room deleted successfully");

    }



    @Override
    public APIResponse<List<RoomDto>> searchByParams(Specification<Room> params) {
        List<Room> rooms = roomRepository.findAll(params);

        List<RoomDto> roomDtoList = rooms
                .stream()
                .map(room -> RoomDto.builder()
                        .id(room.getId())
                        .roomNumber(room.getRoomNumber())
                        .bedNumber(room.getBedNumber())
                        .roomInfo(room.getRoomInfo())
                        .floorNumber(room.getFloorNumber())
                        .status(room.getRoomStatus())
                        .build())
                .toList();

        return APIResponse.ok(roomDtoList, "Rooms fetched successfully");
    }



}
