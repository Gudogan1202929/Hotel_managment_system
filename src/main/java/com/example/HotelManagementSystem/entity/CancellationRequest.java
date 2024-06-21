package com.example.HotelManagementSystem.entity;

import com.example.HotelManagementSystem.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "Cancellation_Requests")
public class CancellationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Reservation reservation;

    @Column(name = "requested_at")
    private LocalDateTime requestedAt = LocalDateTime.now();
}