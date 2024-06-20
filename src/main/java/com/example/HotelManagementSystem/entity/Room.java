package com.example.HotelManagementSystem.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "floor_number", nullable = false)
    private Integer floorNumber;

    @ManyToOne
    @JoinColumn(name = "room_class_id", nullable = false)
    private RoomClass roomClass;

    @Enumerated
    @Column(name = "room_status", nullable = false)
    private String roomStatus;

    @Column(name = "room_number", nullable = false)
    private Integer roomNumber;

    @OneToMany(mappedBy = "room")
    private List<Reservation> reservations;


}
