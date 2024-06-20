package com.example.HotelManagementSystem.controller;


import com.example.HotelManagementSystem.dto.CancellationRequestDto;
import com.example.HotelManagementSystem.dto.response.APIResponse;
import com.example.HotelManagementSystem.service.CancellationRequestServiceInt;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/CancellationRequest")
public class CancellationRequestController {

    private final CancellationRequestServiceInt cancellationRequestServiceInt;

    @Autowired
    public CancellationRequestController(CancellationRequestServiceInt cancellationRequestServiceInt) {
        this.cancellationRequestServiceInt = cancellationRequestServiceInt;
    }

    @GetMapping
    public ResponseEntity<Object> getAllCancellationRequest() {
        log.info("Request to get all cancellation request");
        APIResponse<List<CancellationRequestDto>> response = cancellationRequestServiceInt.getAllCancellations();
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }


    @PostMapping
    public ResponseEntity<Object> createCancellationRequest(@Valid @RequestBody CancellationRequestDto cancellationRequestDto) {
        log.info("Request to create a new cancellation request: {}", cancellationRequestDto);
        APIResponse<CancellationRequestDto> response = cancellationRequestServiceInt.createCancellationRequest(cancellationRequestDto);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> getCancellationRequestById(@PathVariable Long id) {
        log.info("Request to get cancellation request by id: {}", id);
        APIResponse<CancellationRequestDto> response = cancellationRequestServiceInt.getCancellationById(id);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCustomer(@PathVariable Long id, @Valid @RequestBody CancellationRequestDto cancellationRequestDto) {
        log.info("Request to update cancellation request by id: {}", id);
        APIResponse<CancellationRequestDto> response = cancellationRequestServiceInt.updateCancellationRequest(id, cancellationRequestDto);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable Long id) {
        log.info("Request to delete cancellation request by id: {}", id);
        APIResponse<CancellationRequestDto> response = cancellationRequestServiceInt.deleteCancellationRequest(id);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }
}
