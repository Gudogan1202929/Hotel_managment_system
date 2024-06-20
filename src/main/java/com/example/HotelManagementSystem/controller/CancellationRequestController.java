package com.example.HotelManagementSystem.controller;


import com.example.HotelManagementSystem.dto.CancellationRequestDto;
import com.example.HotelManagementSystem.dto.response.APIResponse;
import com.example.HotelManagementSystem.entity.CancellationRequest;
import com.example.HotelManagementSystem.service.CancellationRequestServiceInt;
import com.example.HotelManagementSystem.user.entity.User;
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
@RequestMapping(value = "/api/v1/CancellationRequest")
@Tag(name = "example", description = "the example API")
public class CancellationRequestController {

    private final CancellationRequestServiceInt cancellationRequestServiceInt;

    @Autowired
    public CancellationRequestController(CancellationRequestServiceInt cancellationRequestServiceInt) {
        this.cancellationRequestServiceInt = cancellationRequestServiceInt;
    }


    @GetMapping
    @Operation(summary = "Example endpoint", description = "Description of the example endpoint")
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

    @PostMapping("/{requestId}/approve")
    public ResponseEntity<Object> approveCancellationRequest(@PathVariable Long requestId) {
        log.info("Request to approve cancellation request by id: {}", requestId);
        APIResponse<CancellationRequestDto> response = cancellationRequestServiceInt.approveCancellationRequest(requestId);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }


    @PostMapping("/{requestId}/reject")
    public ResponseEntity<Object> rejectCancellationRequest(@PathVariable Long requestId) {
        log.info("Request to reject cancellation request by id: {}", requestId);
        APIResponse<CancellationRequestDto> response = cancellationRequestServiceInt.rejectCancellationRequest(requestId);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }



    //searching by parameters
    @GetMapping("/search")
    public ResponseEntity<Object> searchCancellationRequest(@RequestParam(required = false) Long customerId,
                                                           @RequestParam(required = false) Long reservationId,
                                                           @RequestParam(required = false) String status) {
        log.info("Request to search cancellation request by parameters: customerId={}, reservationId={}, status={}", customerId, reservationId, status);


        Specification<CancellationRequest> spec = Specification.where(null);

        if (customerId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("customerId"), customerId));
        }

        if (reservationId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("reservationId"), reservationId));
        }

        if (status != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), status));
        }

        APIResponse<List<CancellationRequestDto>> response = cancellationRequestServiceInt.searchByParams(spec);

        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }



}
