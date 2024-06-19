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
@Table(name = "room_class_bed_type")
public class RoomClassBedType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_class_id")
    private RoomClass roomClass;

    @ManyToOne
    @JoinColumn(name = "bed_type_id")
    private BedType bedType;

    @Column(name = "num_beds", nullable = false)
    private Integer numBeds;

}