package com.example.HotelManagementSystem.user.dto;

public class ChangeUserName {
    private String username;
    private String password;
    private String newUserName;

    public ChangeUserName(String username, String password , String newUserName) {
        this.username = username;
        this.password = password;
        this.newUserName = newUserName;
    }

    public ChangeUserName() {
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

    public String getNewUserName() {
        return newUserName;
    }

    public void setNewUserName(String newUserName) {
        this.newUserName = newUserName;
    }
}
