package com.example.HotelManagementSystem.controller;


import com.example.HotelManagementSystem.dto.HousekeepingDto;
import com.example.HotelManagementSystem.dto.response.APIResponse;
import com.example.HotelManagementSystem.service.HousekeepingServiceInt;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/housekeeping")
public class HousekeepingController {

    private final HousekeepingServiceInt housekeepingService;

    @Autowired
    public HousekeepingController(HousekeepingServiceInt housekeepingService) {
        this.housekeepingService = housekeepingService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllHousekeepingTasks() {
        log.info("Request to get all housekeeping tasks");
        APIResponse<List<HousekeepingDto>> response = housekeepingService.getAllHousekeeping();
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @PostMapping
    public ResponseEntity<Object> createHousekeepingTask(@Valid @RequestBody HousekeepingDto housekeepingDto) {
        log.info("Request to create a new housekeeping task: {}", housekeepingDto);
        APIResponse<HousekeepingDto> response = housekeepingService.createHousekeeping(housekeepingDto);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getHousekeepingTaskById(@PathVariable Long id) {
        log.info("Request to get housekeeping task by id: {}", id);
        APIResponse<HousekeepingDto> response = housekeepingService.getHousekeepingById(id);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateHousekeepingTask(@PathVariable Long id, @Valid @RequestBody HousekeepingDto housekeepingDto) {
        log.info("Request to update housekeeping task by id: {}", id);
        APIResponse<HousekeepingDto> response = housekeepingService.updateHousekeeping(id, housekeepingDto);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteHousekeepingTask(@PathVariable Long id) {
        log.info("Request to delete housekeeping task by id: {}", id);
        APIResponse<HousekeepingDto> response = housekeepingService.deleteHousekeeping(id);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }
}