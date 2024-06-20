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
@Table(name = "bed_type")
public class BedType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bed_type_name", nullable = false, length = 50)
    private String bedTypeName;

    @OneToMany(mappedBy = "bedType")
    private List<RoomClassBedType> roomClassBedTypes;
}