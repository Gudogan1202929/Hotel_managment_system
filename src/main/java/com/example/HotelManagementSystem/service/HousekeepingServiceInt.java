package com.example.HotelManagementSystem.service;

import com.example.HotelManagementSystem.dto.HousekeepingDto;
import org.springframework.stereotype.Service;

import com.example.HotelManagementSystem.dto.response.APIResponse;

import java.util.List;

@Service
public interface HousekeepingServiceInt {

    APIResponse<List<HousekeepingDto>> getAllHousekeeping();

    APIResponse<HousekeepingDto> getHousekeepingById(Long id);

    APIResponse<HousekeepingDto> createHousekeeping(HousekeepingDto housekeepingDto);

    APIResponse<HousekeepingDto> updateHousekeeping(Long id, HousekeepingDto housekeepingDto);

    APIResponse<HousekeepingDto> deleteHousekeeping(Long id);


}
