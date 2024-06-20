package com.example.HotelManagementSystem.controller;


import com.example.HotelManagementSystem.dto.InvoiceDto;
import com.example.HotelManagementSystem.dto.response.APIResponse;
import com.example.HotelManagementSystem.entity.Invoice;
import com.example.HotelManagementSystem.service.InvoiceServiceInt;
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
@Tag(name = "Invoice", description = "All invoice related endpoints")
@RequestMapping(value = "/api/v1/invoices")
public class InvoiceController {

    private final InvoiceServiceInt invoiceService;

    @Autowired
    public InvoiceController(InvoiceServiceInt invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Operation(summary = "Retriving all invoices", description = "This endpoint is used to get all invoices")
    @GetMapping
    public ResponseEntity<Object> getAllInvoices() {
        log.info("Request to get all invoices");
        APIResponse<List<InvoiceDto>> response = invoiceService.getAllInvoices();
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @Operation(summary = "Create new invoice", description = "This endpoint used for adding new invoice")
    @PostMapping
    public ResponseEntity<Object> createInvoice(@Valid @RequestBody InvoiceDto invoiceDto) {
        log.info("Request to create a new invoice: {}", invoiceDto);
        APIResponse<InvoiceDto> response = invoiceService.createInvoice(invoiceDto);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @Operation(summary = "Get invoice by id", description = "This endpoint is used to get invoice by id")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getInvoiceById(@PathVariable Long id) {
        log.info("Request to get invoice by id: {}", id);
        APIResponse<InvoiceDto> response = invoiceService.getInvoiceById(id);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @Operation(summary = "Update invoice by id", description = "This endpoint is used to update invoice by id")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateInvoice(@PathVariable Long id, @Valid @RequestBody InvoiceDto invoiceDto) {
        log.info("Request to update invoice by id: {}", id);
        APIResponse<InvoiceDto> response = invoiceService.updateInvoice(id, invoiceDto);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @Operation(summary = "Delete invoice by id", description = "This endpoint is used to delete invoice by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteInvoice(@PathVariable Long id) {
        log.info("Request to delete invoice by id: {}", id);
        APIResponse<InvoiceDto> response = invoiceService.deleteInvoice(id);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @Operation(summary = "Search invoice by parameters", description = "This endpoint is used to search invoice by parameters")
    public ResponseEntity<Object> searchInvoice(@RequestParam(required = false) Long reservationId,
                                                 @RequestParam(required = false) Double amount,
                                                 @RequestParam(required = false) String status) {
        log.info("Request to search invoice by parameters: reservationId={}, amount={}, status={}", reservationId, amount, status);

         Specification<Invoice> spec = Specification.where(null);


         if (reservationId != null) {
             spec = spec.and((root, query, cb) -> cb.equal(root.get("reservation").get("id"), reservationId));
         }

         if (amount != null) {
             spec = spec.and((root, query, cb) -> cb.equal(root.get("amount"), amount));
         }

         if (status != null) {
             spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), status));
         }



        APIResponse<List<InvoiceDto>> response = invoiceService.searchByParams(spec);

        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }
}