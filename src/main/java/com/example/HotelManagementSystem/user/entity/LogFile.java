package com.example.HotelManagementSystem.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "log_file")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LogFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long IPsId;

    @Column(name = "time", nullable = false, columnDefinition = "TEXT")
    private String time;

    @Column(name = "ip", columnDefinition = "TEXT")
    private String ip;

    @Column(name = "URL", columnDefinition = "TEXT")
    private String URL;

}
