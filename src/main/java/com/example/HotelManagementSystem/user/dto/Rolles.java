package com.example.HotelManagementSystem.user.dto;

import java.util.ArrayList;
import java.util.List;

public class Rolles {
    private String role;
    private List<String> paths;

    public Rolles(String role) {
        this.role = role;
        this.paths = new ArrayList<>();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<String> getPaths() {
        return paths;
    }

    public void setPaths(List<String> paths) {
        this.paths = paths;
    }
}
