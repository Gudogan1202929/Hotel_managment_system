package com.example.HotelManagementSystem.service;

import com.example.HotelManagementSystem.dto.InvoiceDto;
import com.example.HotelManagementSystem.dto.response.APIResponse;
import com.example.HotelManagementSystem.entity.Invoice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InvoiceServiceInt {

    APIResponse<List<InvoiceDto>> getAllInvoices();

    APIResponse<InvoiceDto> getInvoiceById(Long id);

    APIResponse<InvoiceDto> createInvoice(InvoiceDto invoiceDto);

    APIResponse<InvoiceDto> updateInvoice(Long id, InvoiceDto invoiceDto);

    APIResponse<InvoiceDto> deleteInvoice(Long id);

    APIResponse<List<InvoiceDto>> searchByParams(Specification<Invoice> params);



}
