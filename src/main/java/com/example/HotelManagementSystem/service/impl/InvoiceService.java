package com.example.HotelManagementSystem.service.impl;

import com.example.HotelManagementSystem.dto.InvoiceDto;
import com.example.HotelManagementSystem.entity.Invoice;
import com.example.HotelManagementSystem.exception.BadRequestException;
import com.example.HotelManagementSystem.exception.ResourceNotFoundException;
import com.example.HotelManagementSystem.repository.InvoiceRepo;
import com.example.HotelManagementSystem.repository.RoomReservationRepo;
import com.example.HotelManagementSystem.service.InvoiceServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.HotelManagementSystem.dto.response.APIResponse;

import java.util.List;

@Service
public class InvoiceService implements InvoiceServiceInt {

    private final InvoiceRepo invoiceRepository;
    private final RoomReservationRepo reservationRepository;

    @Autowired
    public InvoiceService(InvoiceRepo invoiceRepository, RoomReservationRepo reservationRepository) {
        this.invoiceRepository = invoiceRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public APIResponse<List<InvoiceDto>> getAllInvoices() {
        List<Invoice> invoiceList = invoiceRepository.findAll();

        List<InvoiceDto> invoiceDtoList = invoiceList
                .stream()
                .map(invoice -> InvoiceDto.builder()
                        .id(invoice.getId())
                        .reservationId(invoice.getReservation().getId())
                        .amount(invoice.getAmount())
                        .createdAt(invoice.getCreatedAt())
                        .status(invoice.getStatus())
                        .build())
                .toList();

        return APIResponse.ok(invoiceDtoList, "Invoices fetched successfully");

    }

    @Override
    public APIResponse<InvoiceDto> getInvoiceById(Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElse(null);

        if (invoice == null) {
            throw new ResourceNotFoundException(Invoice.class, "id", id.toString());
        }

        InvoiceDto invoiceDto = InvoiceDto.builder()
                .id(invoice.getId())
                .reservationId(invoice.getReservation().getId())
                .amount(invoice.getAmount())
                .createdAt(invoice.getCreatedAt())
                .status(invoice.getStatus())
                .build();

        return APIResponse.ok(invoiceDto, "Invoice fetched successfully");
    }

    @Override
    public APIResponse<InvoiceDto> createInvoice(InvoiceDto invoiceDto) {

        //id should be null when creating a new invoice
        if (invoiceDto.getId() != null) {
            throw new BadRequestException("Id should be null when creating a new invoice");
        }

        //if an invoice with the same reservation id already exists
        if (invoiceRepository.findByReservationId(invoiceDto.getReservationId()) != null) {
            throw new BadRequestException("Invoice with reservation id " + invoiceDto.getReservationId() + " already exists");
        }

        //check if the reservation exists
        if (reservationRepository.findById(invoiceDto.getReservationId()).orElse(null) == null) {
            throw new BadRequestException("Reservation with id " + invoiceDto.getReservationId() + " does not exist");
        }

        Invoice invoice = Invoice.builder()
                .reservation(reservationRepository.findById(invoiceDto.getReservationId()).get())
                .amount(invoiceDto.getAmount())
                .status(invoiceDto.getStatus())
                .build();

        Invoice savedInvoice = invoiceRepository.save(invoice);

        InvoiceDto savedInvoiceDto = InvoiceDto.builder()
                .id(savedInvoice.getId())
                .reservationId(savedInvoice.getReservation().getId())
                .amount(savedInvoice.getAmount())
                .createdAt(savedInvoice.getCreatedAt())
                .status(savedInvoice.getStatus())
                .build();

        return APIResponse.created(savedInvoiceDto, "Invoice created successfully");
    }


    @Override
    public APIResponse<InvoiceDto> updateInvoice(Long id, InvoiceDto invoiceDto) {
        Invoice invoice = invoiceRepository.findById(id).orElse(null);

        if (invoice == null) {
            throw new ResourceNotFoundException(Invoice.class, "id", id.toString());
        }

        invoice.setAmount(invoiceDto.getAmount());
        invoice.setStatus(invoiceDto.getStatus());

        Invoice updatedInvoice = invoiceRepository.save(invoice);

        InvoiceDto updatedInvoiceDto = InvoiceDto.builder()
                .id(updatedInvoice.getId())
                .reservationId(updatedInvoice.getReservation().getId())
                .amount(updatedInvoice.getAmount())
                .createdAt(updatedInvoice.getCreatedAt())
                .status(updatedInvoice.getStatus())
                .build();

        return APIResponse.ok(updatedInvoiceDto, "Invoice updated successfully");
    }

    @Override
    public APIResponse<InvoiceDto> deleteInvoice(Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElse(null);

        if (invoice == null) {
            throw new ResourceNotFoundException(Invoice.class, "id", id.toString());
        }

        invoiceRepository.delete(invoice);

        InvoiceDto deletedInvoiceDto = InvoiceDto.builder()
                .id(invoice.getId())
                .reservationId(invoice.getReservation().getId())
                .amount(invoice.getAmount())
                .createdAt(invoice.getCreatedAt())
                .status(invoice.getStatus())
                .build();

        return APIResponse.ok(deletedInvoiceDto, "Invoice deleted successfully");

    }

    @Override
    public APIResponse<List<InvoiceDto>> searchByParams(Specification<Invoice> params) {
        List<Invoice> invoices = invoiceRepository.findAll(params);

        List<InvoiceDto> invoiceDtoList = invoices
                .stream()
                .map(invoice -> InvoiceDto.builder()
                        .id(invoice.getId())
                        .reservationId(invoice.getReservation().getId())
                        .amount(invoice.getAmount())
                        .createdAt(invoice.getCreatedAt())
                        .status(invoice.getStatus())
                        .build())
                .toList();

        return APIResponse.ok(invoiceDtoList, "Invoices fetched successfully");
    }




}
