package com.example.HotelManagementSystem.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private RoomStatus roomStatus;

    @Column(name = "room_number", nullable = false)
    private Integer roomNumber;


}
