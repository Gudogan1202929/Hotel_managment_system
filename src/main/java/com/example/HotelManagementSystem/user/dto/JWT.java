package com.example.HotelManagementSystem.user.dto;

public class JWT{
    private String JWTToken;

    public JWT(String JWTToken) {
        this.JWTToken = JWTToken;
    }

    public JWT() {
    }

    public String getJWTToken() {
        return JWTToken;
    }

    public void setJWTToken(String JWTToken) {
        this.JWTToken = JWTToken;
    }

    @Override
    public String toString() {
        return JWTToken;
    }
}
