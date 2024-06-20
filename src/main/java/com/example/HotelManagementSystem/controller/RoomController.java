package com.example.HotelManagementSystem.controller;


import com.example.HotelManagementSystem.dto.RoomDto;
import com.example.HotelManagementSystem.dto.response.APIResponse;
import com.example.HotelManagementSystem.service.RoomServiceInt;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/rooms")
public class RoomController {

    private final RoomServiceInt roomService;

    @Autowired
    public RoomController(RoomServiceInt roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllRooms() {
        log.info("Request to get all rooms");
        APIResponse<List<RoomDto>> response = roomService.getAllRooms();
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @PostMapping
    public ResponseEntity<Object> createRoom(@Valid @RequestBody RoomDto roomDto) {
        log.info("Request to create a new room: {}", roomDto);
        APIResponse<RoomDto> response = roomService.createRoom(roomDto);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getRoomById(@PathVariable Long id) {
        log.info("Request to get room by id: {}", id);
        APIResponse<RoomDto> response = roomService.getRoomById(id);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRoom(@PathVariable Long id, @Valid @RequestBody RoomDto roomDto) {
        log.info("Request to update room by id: {}", id);
        APIResponse<RoomDto> response = roomService.updateRoom(id, roomDto);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRoom(@PathVariable Long id) {
        log.info("Request to delete room by id: {}", id);
        APIResponse<RoomDto> response = roomService.deleteRoom(id);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }
}