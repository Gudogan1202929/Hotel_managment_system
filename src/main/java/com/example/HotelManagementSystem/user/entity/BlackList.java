package com.example.HotelManagementSystem.user.entity;

import jakarta.persistence.*;

@Table
@Entity(name = "black_list")
public class BlackList {
        @Id
        @SequenceGenerator(name = "black_tokens_sequence",
                sequenceName = "black_tokens_sequence",
                allocationSize = 1)
        @GeneratedValue(strategy = GenerationType.SEQUENCE,
                generator = "bblack_tokens_sequence")
        private long blackId;

        @Column(name = "black_token",
                nullable = false,
                columnDefinition = "TEXT")
        private String token;

        public BlackList(String token) {
            this.token = token;
        }

        public BlackList() {
        }

}
