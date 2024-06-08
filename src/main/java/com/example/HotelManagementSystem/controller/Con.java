package com.example.HotelManagementSystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/hi")
public class Con {

    @GetMapping("/hi")
    public String hi() {
        return "hi";
    }
}
