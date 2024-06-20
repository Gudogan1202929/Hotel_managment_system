package com.example.HotelManagementSystem.entity;

import com.example.HotelManagementSystem.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PENDING;

    @Column(name = "requested_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestedAt;

    @PrePersist
    protected void onRequest() {
        requestedAt = new Date();
    }


    public enum Status {
        PENDING,
        APPROVED,
        REJECTED
    }
}