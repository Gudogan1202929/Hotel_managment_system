package com.example.HotelManagementSystem.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "Reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(name = "check_in_date")
    private Date checkInDate;

    @Column(name = "check_out_date")
    private Date checkOutDate;

    @Column(name = "created_at", insertable = false, updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    private List<CancellationRequest> cancellationRequests;

    @PrePersist
    public void prePersist() {
        createdAt = new Date();
    }

}
