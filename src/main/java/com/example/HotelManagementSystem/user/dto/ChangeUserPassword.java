package com.example.HotelManagementSystem.user.dto;

public class ChangeUserPassword {
    private String username;
    private String oldPassword;
    private String newPassword;

    public ChangeUserPassword(String username, String oldPassword, String newPassword) {
        this.username = username;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public ChangeUserPassword() {
    }

    public String getUsername() {
        return username;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
