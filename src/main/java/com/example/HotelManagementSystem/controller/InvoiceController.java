package com.example.HotelManagementSystem.controller;


import com.example.HotelManagementSystem.dto.InvoiceDto;
import com.example.HotelManagementSystem.dto.response.APIResponse;
import com.example.HotelManagementSystem.service.InvoiceServiceInt;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/invoices")
public class InvoiceController {

    private final InvoiceServiceInt invoiceService;

    @Autowired
    public InvoiceController(InvoiceServiceInt invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllInvoices() {
        log.info("Request to get all invoices");
        APIResponse<List<InvoiceDto>> response = invoiceService.getAllInvoices();
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @PostMapping
    public ResponseEntity<Object> createInvoice(@Valid @RequestBody InvoiceDto invoiceDto) {
        log.info("Request to create a new invoice: {}", invoiceDto);
        APIResponse<InvoiceDto> response = invoiceService.createInvoice(invoiceDto);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getInvoiceById(@PathVariable Long id) {
        log.info("Request to get invoice by id: {}", id);
        APIResponse<InvoiceDto> response = invoiceService.getInvoiceById(id);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateInvoice(@PathVariable Long id, @Valid @RequestBody InvoiceDto invoiceDto) {
        log.info("Request to update invoice by id: {}", id);
        APIResponse<InvoiceDto> response = invoiceService.updateInvoice(id, invoiceDto);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteInvoice(@PathVariable Long id) {
        log.info("Request to delete invoice by id: {}", id);
        APIResponse<InvoiceDto> response = invoiceService.deleteInvoice(id);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }
}