package com.example.HotelManagementSystem.service.impl;

import com.example.HotelManagementSystem.dto.RoomDto;
import com.example.HotelManagementSystem.dto.response.APIResponse;
import com.example.HotelManagementSystem.entity.Room;
import com.example.HotelManagementSystem.entity.RoomClass;
import com.example.HotelManagementSystem.entity.RoomStatus;
import com.example.HotelManagementSystem.exception.BadRequestException;
import com.example.HotelManagementSystem.exception.ResourceNotFoundException;
import com.example.HotelManagementSystem.repository.*;
import com.example.HotelManagementSystem.service.RoomServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService implements RoomServiceInt {

    private final RoomRepo roomRepository;
    private final RoomClassRepo roomClassRepository;
    private final RoomStatusRepo roomStatusRepository;
    private final FeatureRepo featureRepo;
    private final BedTypeRepo bedTypeRepo;
    private final RoomClassBedTypeRepo roomClassBedTypeRepo;
    private final RoomClassFeatureRepo roomClassFeatureRepo;


    @Autowired
    public RoomService(RoomRepo roomRepository, RoomClassRepo roomClassRepository, RoomStatusRepo roomStatusRepository,
                       FeatureRepo featureRepo, BedTypeRepo bedTypeRepo, RoomClassBedTypeRepo roomClassBedTypeRepo,
                       RoomClassFeatureRepo roomClassFeatureRepo) {
        this.roomRepository = roomRepository;
        this.roomClassRepository = roomClassRepository;
        this.roomStatusRepository = roomStatusRepository;
        this.featureRepo = featureRepo;
        this.bedTypeRepo = bedTypeRepo;
        this.roomClassBedTypeRepo = roomClassBedTypeRepo;
        this.roomClassFeatureRepo = roomClassFeatureRepo;

    }

    @Override
    public APIResponse<List<RoomDto>> getAllRooms() {
        List<Room> roomList = roomRepository.findAll();

        List<RoomDto> roomDtoList = roomList
                .stream()
                .map(room -> RoomDto.builder()
                        .id(room.getId())
                        .roomNumber(room.getRoomNumber())
                        .roomClassId(room.getRoomClass().getId())
                        .floorNumber(room.getFloorNumber())
                        .statusId(room.getRoomStatus().getId())
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
                .roomClassId(room.getRoomClass().getId())
                .floorNumber(room.getFloorNumber())
                .statusId(room.getRoomStatus().getId())
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

        //check if the room class exists
        if (roomClassRepository.findById(roomDto.getRoomClassId()).isEmpty()) {
            throw new ResourceNotFoundException(RoomClass.class, "id", roomDto.getRoomClassId().toString());
        }

        //check if the room status exists
        if (roomStatusRepository.findById(roomDto.getStatusId()).isEmpty()) {
            throw new ResourceNotFoundException(RoomStatus.class, "id", roomDto.getStatusId().toString());
        }


        return null;
    }

    @Override
    public APIResponse<RoomDto> updateRoom(Long id, RoomDto roomDto) {
        return null;
    }

    @Override
    public APIResponse<RoomDto> deleteRoom(Long id) {
        return null;
    }

}
