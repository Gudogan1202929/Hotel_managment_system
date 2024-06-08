package com.example.HotelManagementSystem.user.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "user_table")
public class User {
    @Id
    @SequenceGenerator(name = "user_table_sequence",
            sequenceName = "user_table_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "user_table_sequence")
    private long userid;

    @Column(name = "username", nullable = false, columnDefinition = "TEXT")
    private String username;

    @Column(name = "password", nullable = false, columnDefinition = "TEXT")
    private String password;

    @Column(name = "token", columnDefinition = "TEXT")
    private String token;

    @Column(name = "role", columnDefinition = "TEXT")
    private String role;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User() {
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
