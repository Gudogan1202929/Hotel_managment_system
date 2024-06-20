package com.example.HotelManagementSystem.controller;


import com.example.HotelManagementSystem.dto.HousekeepingDto;
import com.example.HotelManagementSystem.dto.response.APIResponse;
import com.example.HotelManagementSystem.entity.Housekeeping;
import com.example.HotelManagementSystem.service.HousekeepingServiceInt;
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
@Tag(name = "Housekeeping", description = "All housekeeping related endpoints")
@RequestMapping(value = "/api/v1/housekeeping")
public class HousekeepingController {

    private final HousekeepingServiceInt housekeepingService;

    @Autowired
    public HousekeepingController(HousekeepingServiceInt housekeepingService) {
        this.housekeepingService = housekeepingService;
    }

    @Operation(summary = "Retriving all housekeeping tasks", description = "This endpoint is used to get all housekeeping tasks")
    @GetMapping
    public ResponseEntity<Object> getAllHousekeepingTasks() {
        log.info("Request to get all housekeeping tasks");
        APIResponse<List<HousekeepingDto>> response = housekeepingService.getAllHousekeeping();
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

@Operation(summary = "Create new housekeeping task", description = "This endpoint used for adding new housekeeping task")
    @PostMapping
    public ResponseEntity<Object> createHousekeepingTask(@Valid @RequestBody HousekeepingDto housekeepingDto) {
        log.info("Request to create a new housekeeping task: {}", housekeepingDto);
        APIResponse<HousekeepingDto> response = housekeepingService.createHousekeeping(housekeepingDto);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

@Operation(summary = "Get housekeeping task by id", description = "This endpoint is used to get housekeeping task by id")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getHousekeepingTaskById(@PathVariable Long id) {
        log.info("Request to get housekeeping task by id: {}", id);
        APIResponse<HousekeepingDto> response = housekeepingService.getHousekeepingById(id);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @Operation(summary = "Update housekeeping task by id", description = "This endpoint is used to update housekeeping task by id")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateHousekeepingTask(@PathVariable Long id, @Valid @RequestBody HousekeepingDto housekeepingDto) {
        log.info("Request to update housekeeping task by id: {}", id);
        APIResponse<HousekeepingDto> response = housekeepingService.updateHousekeeping(id, housekeepingDto);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @Operation(summary = "Delete housekeeping task by id", description = "This endpoint is used to delete housekeeping task by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteHousekeepingTask(@PathVariable Long id) {
        log.info("Request to delete housekeeping task by id: {}", id);
        APIResponse<HousekeepingDto> response = housekeepingService.deleteHousekeeping(id);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @Operation(summary = "Search housekeeping task by parameters", description = "This endpoint is used to search housekeeping task by parameters")
    @GetMapping("/search")
    public ResponseEntity<Object> searchHousekeepingTask(@RequestParam(required = false) Long roomId,
                                                         @RequestParam(required = false) Long employeeId,
                                                         @RequestParam(required = false) String status,
                                                         @RequestParam(required = false) String taskDate) {
        log.info("Request to search housekeeping task by parameters: roomId={}, employeeId={}, status={}, taskDate={}", roomId, employeeId, status, taskDate);

         Specification<Housekeeping> spec = Specification.where(null);


         if (roomId != null) {
             spec = spec.and((root, query, cb) -> cb.equal(root.get("roomId"), roomId));
         }

         if (employeeId != null) {
             spec = spec.and((root, query, cb) -> cb.equal(root.get("employeeId"), employeeId));
         }

         if (status != null) {
             spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), status));
         }

         if (taskDate != null) {
             spec = spec.and((root, query, cb) -> cb.equal(root.get("taskDate"), taskDate));
         }


         APIResponse<List<HousekeepingDto>> response = housekeepingService.searchByParams(spec);

         return ResponseEntity.status(response.getHttpStatus()).body(response);
    }
}