package com.example.HotelManagementSystem.user.dto;

public class ChangeRole {

    private String username;
    private String password;
    private String newRole;

    public ChangeRole(String username, String password , String newRole) {
        this.username = username;
        this.password = password;
        this.newRole = newRole;
    }

    public ChangeRole() {
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

    public String getNewRole() {
        return newRole;
    }

    public void setNewRole(String newRole) {
        this.newRole = newRole;
    }
}
