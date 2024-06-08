package com.example.HotelManagementSystem.user.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "log_file")
public class LogFile {
    @Id
    @SequenceGenerator(name = "log_file_sequence",
            sequenceName = "log_file_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "log_file_sequence")
    private long IPsId;

    @Column(name = "time", nullable = false, columnDefinition = "TEXT")
    private String time;

    @Column(name = "ip", nullable = false, columnDefinition = "TEXT")
    private String ip;

    @Column(name = "URL", columnDefinition = "TEXT")
    private String URL;

    public LogFile(String time, String ip, String URL) {
        this.time = time;
        this.ip = ip;
        this.URL = URL;
    }

    public LogFile() {
    }

    public long getIPsId() {
        return IPsId;
    }

    public void setIPsId(long IPsId) {
        this.IPsId = IPsId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
