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
@Entity
@Builder
@Table(name = "room_class")
public class RoomClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "class_name", nullable = false, length = 100)
    private String className;

    @Column(name = "base_price", nullable = false,columnDefinition = "DECIMAL(10, 2)")
    private Double basePrice;

    @OneToMany(mappedBy = "roomClass")
    private List<RoomClassBedType> roomClassBedTypes;

    @OneToMany(mappedBy = "roomClass")
    private List<RoomClassFeature> roomClassFeatures;
}