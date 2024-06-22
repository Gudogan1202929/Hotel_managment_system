package com.example.HotelManagementSystem.dto;

import com.example.HotelManagementSystem.entity.Invoice;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class InvoiceDto {
        private Long id;

        @NotNull(message = "Reservation ID is required")
        private Long reservationId;

        @NotNull(message = "Amount is required")
        private Double amount;

        @NotNull(message = "Status is required")
        private Invoice.Status status;

        private LocalDateTime createdAt = LocalDateTime.now();
}
