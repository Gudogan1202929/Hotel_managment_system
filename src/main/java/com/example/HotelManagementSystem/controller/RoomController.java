package com.example.HotelManagementSystem.controller;

import com.example.HotelManagementSystem.dto.RoomDto;
import com.example.HotelManagementSystem.dto.response.APIResponse;
import com.example.HotelManagementSystem.entity.Room;
import com.example.HotelManagementSystem.service.RoomServiceInt;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Tag(name = "Room", description = "All room related endpoints")
@RequestMapping(value = "/api/v1/rooms")
public class RoomController {

    private final RoomServiceInt roomService;

    @Autowired
    public RoomController(RoomServiceInt roomService) {
        this.roomService = roomService;
    }

    @Operation(summary = "Retriving all rooms", description = "This endpoint is used to get all rooms")
    @GetMapping
    public ResponseEntity<Object> getAllRooms() {
        log.info("Request to get all rooms");
        APIResponse<List<RoomDto>> response = roomService.getAllRooms();
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @Operation(summary = "Create new room", description = "This endpoint used for adding new room")
    @PostMapping
    public ResponseEntity<Object> createRoom(@Valid @RequestBody RoomDto roomDto) {
        log.info("Request to create a new room: {}", roomDto);
        APIResponse<RoomDto> response = roomService.createRoom(roomDto);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @Operation(summary = "Get room by id", description = "This endpoint is used to get room by id")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getRoomById(@PathVariable Long id) {
        log.info("Request to get room by id: {}", id);
        APIResponse<RoomDto> response = roomService.getRoomById(id);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @Operation(summary = "Update room by id", description = "This endpoint is used to update room by id")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRoom(@PathVariable Long id, @Valid @RequestBody RoomDto roomDto) {
        log.info("Request to update room by id: {}", id);
        APIResponse<RoomDto> response = roomService.updateRoom(id, roomDto);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @Operation(summary = "Delete room by id", description = "This endpoint is used to delete room by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRoom(@PathVariable Long id) {
        log.info("Request to delete room by id: {}", id);
        APIResponse<RoomDto> response = roomService.deleteRoom(id);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @Operation(summary = "Search room by parameters", description = "This endpoint is used to search room by parameters")
    @GetMapping("/search")
    public ResponseEntity<Object> searchRoom(@RequestParam(required = false) String floorNumber,
                                             @RequestParam(required = false) String roomStatus,
                                             @RequestParam(required = false) String roomNumber,
                                             @RequestParam(required = false) String bedNumber,
                                             @RequestParam(required = false) String info) {
        log.info("Request to search room by parameters: floorNumber={}, roomStatus={}, roomNumber={}, bedNumber={}, info={}", floorNumber, roomStatus, roomNumber, bedNumber, info);

        Specification<Room> spec = Specification.where(null);

        if (floorNumber != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("floorNumber"), floorNumber));
        }

        if (roomStatus != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("roomStatus"), roomStatus));
        }

        if (roomNumber != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("roomNumber"), roomNumber));
        }

        if (bedNumber != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("bedNumber"), bedNumber));
        }

        if (info != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("info"), info));
        }


        APIResponse<List<RoomDto>> response = roomService.searchByParams(spec);

        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }
}