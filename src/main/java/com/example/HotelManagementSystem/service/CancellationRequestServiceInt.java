package com.example.HotelManagementSystem.service;

import com.example.HotelManagementSystem.dto.CancellationRequestDto;
import com.example.HotelManagementSystem.dto.response.APIResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CancellationRequestServiceInt {
    APIResponse<List<CancellationRequestDto>> getAllCancelation();
}
