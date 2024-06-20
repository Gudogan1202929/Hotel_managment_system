package com.example.HotelManagementSystem.service.impl;

import com.example.HotelManagementSystem.dto.CancellationRequestDto;
import com.example.HotelManagementSystem.dto.response.APIResponse;
import com.example.HotelManagementSystem.entity.CancellationRequest;
import com.example.HotelManagementSystem.entity.Customer;
import com.example.HotelManagementSystem.repository.CancellationRequestRepo;
import com.example.HotelManagementSystem.service.CancellationRequestServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CancellationRequestService implements CancellationRequestServiceInt {

    private final CancellationRequestRepo cancellationRequestRepo;


     @Autowired
     public CancellationRequestService(CancellationRequestRepo cancellationRequestRepo) {
          this.cancellationRequestRepo = cancellationRequestRepo;
     }

     @Override
     public APIResponse<List<CancellationRequestDto>> getAllCancelation() {
//          List<CancellationRequest> cancellationRequests = cancellationRequestRepo.findAll();
//
//          List<CancellationRequestDto> cancellationRequestDtos = cancellationRequests
//                  .stream()
//                  .map(cancellationRequest -> CancellationRequestDto.builder()
//                          .id(cancellationRequest.getId())
//                          .
//
//                  .toList();
//
//          return APIResponse.ok(customerDTOList, "Customers fetched successfully");
         return null;
     }


}
