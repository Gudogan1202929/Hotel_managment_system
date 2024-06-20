package com.example.HotelManagementSystem.service;

import com.example.HotelManagementSystem.dto.CancellationRequestDto;
import com.example.HotelManagementSystem.dto.ReservationDto;
import com.example.HotelManagementSystem.dto.response.APIResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CancellationRequestServiceInt {

    APIResponse<List<CancellationRequestDto>> getAllCancellations();

    APIResponse<CancellationRequestDto> getCancellationById(Long id);

    APIResponse<CancellationRequestDto> createCancellationRequest(CancellationRequestDto cancellationRequestDto);

    APIResponse<CancellationRequestDto> updateCancellationRequest(Long id, CancellationRequestDto cancellationRequestDto);

    APIResponse<CancellationRequestDto> deleteCancellationRequest(Long id);

    APIResponse<CancellationRequestDto>  approveCancellationRequest(Long requestId) ;

    APIResponse<CancellationRequestDto> rejectCancellationRequest(Long requestId);
}
