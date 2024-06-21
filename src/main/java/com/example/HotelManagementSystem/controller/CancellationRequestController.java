package com.example.HotelManagementSystem.controller;


import com.example.HotelManagementSystem.dto.CancellationRequestDto;
import com.example.HotelManagementSystem.dto.response.APIResponse;
import com.example.HotelManagementSystem.entity.CancellationRequest;
import com.example.HotelManagementSystem.service.CancellationRequestServiceInt;
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
@Tag(name = "CancellationRequest", description = "All cancellation request related endpoints")
public class CancellationRequestController {

    private final CancellationRequestServiceInt cancellationRequestServiceInt;

    @Autowired
    public CancellationRequestController(CancellationRequestServiceInt cancellationRequestServiceInt) {
        this.cancellationRequestServiceInt = cancellationRequestServiceInt;
    }

    @GetMapping
    @Operation(summary = "Retriving all cancellation request", description = "This endpoint is used to get all cancellation request")
    public ResponseEntity<Object> getAllCancellationRequest() {
        log.info("Request to get all cancellation request");
        APIResponse<List<CancellationRequestDto>> response = cancellationRequestServiceInt.getAllCancellations();
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }


    @Operation(summary = "Create new cancellation request", description = "This endpoint used for adding new cancellation request")
    @PostMapping
    public ResponseEntity<Object> createCancellationRequest(@Valid @RequestBody CancellationRequestDto cancellationRequestDto) {
        log.info("Request to create a new cancellation request: {}", cancellationRequestDto);
        APIResponse<CancellationRequestDto> response = cancellationRequestServiceInt.createCancellationRequest(cancellationRequestDto);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }


    @Operation(summary = "Get cancellation request by id", description = "This endpoint is used to get cancellation request by id")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getCancellationRequestById(@PathVariable Long id) {
        log.info("Request to get cancellation request by id: {}", id);
        APIResponse<CancellationRequestDto> response = cancellationRequestServiceInt.getCancellationById(id);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @Operation(summary = "Update cancellation request by id", description = "This endpoint is used to update cancellation request by id")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCustomer(@PathVariable Long id, @Valid @RequestBody CancellationRequestDto cancellationRequestDto) {
        log.info("Request to update cancellation request by id: {}", id);
        APIResponse<CancellationRequestDto> response = cancellationRequestServiceInt.updateCancellationRequest(id, cancellationRequestDto);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }


    @Operation(summary = "Approve cancellation request by id (By Admin)", description = "This endpoint is used to approve cancellation request by id")
    @PostMapping("/{requestId}/approve")
    public ResponseEntity<Object> approveCancellationRequest(@PathVariable Long requestId) {
        log.info("Request to approve cancellation request by id: {}", requestId);
        APIResponse<CancellationRequestDto> response = cancellationRequestServiceInt.approveCancellationRequest(requestId);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }


    @Operation(summary = "Reject cancellation request by id (By Admin)", description = "This endpoint is used to reject cancellation request by id")
    @PostMapping("/{requestId}/reject")
    public ResponseEntity<Object> rejectCancellationRequest(@PathVariable Long requestId) {
        log.info("Request to reject cancellation request by id: {}", requestId);
        APIResponse<CancellationRequestDto> response = cancellationRequestServiceInt.rejectCancellationRequest(requestId);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }



    @Operation(summary = "Search cancellation request by parameters", description = "This endpoint is used to search cancellation request by parameters")
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
