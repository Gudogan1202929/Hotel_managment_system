package com.example.HotelManagementSystem.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table
@Entity(name = "black_list")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BlackList {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long blackId;

        @Column(name = "black_token",
                nullable = false,
                columnDefinition = "TEXT")
        private String token;////
}
