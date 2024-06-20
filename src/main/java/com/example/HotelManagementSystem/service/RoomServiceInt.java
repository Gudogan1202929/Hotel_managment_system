package com.example.HotelManagementSystem.service;

import com.example.HotelManagementSystem.dto.RoomDto;
import com.example.HotelManagementSystem.dto.response.APIResponse;
import com.example.HotelManagementSystem.entity.Room;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoomServiceInt {

    APIResponse<List<RoomDto>> getAllRooms();

    APIResponse<RoomDto> getRoomById(Long id);

    APIResponse<RoomDto> createRoom(RoomDto roomDto);

    APIResponse<RoomDto> updateRoom(Long id, RoomDto roomDto);

    APIResponse<RoomDto> deleteRoom(Long id);

    APIResponse<List<RoomDto>> searchByParams(Specification<Room> params);

}


