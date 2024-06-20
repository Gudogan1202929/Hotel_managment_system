package com.example.HotelManagementSystem.service.impl;

import com.example.HotelManagementSystem.dto.HousekeepingDto;
import com.example.HotelManagementSystem.dto.response.APIResponse;
import com.example.HotelManagementSystem.entity.Housekeeping;
import com.example.HotelManagementSystem.exception.BadRequestException;
import com.example.HotelManagementSystem.exception.ResourceNotFoundException;
import com.example.HotelManagementSystem.repository.EmployeeRepo;
import com.example.HotelManagementSystem.repository.HouseKeppingRepo;
import com.example.HotelManagementSystem.repository.RoomRepo;
import com.example.HotelManagementSystem.service.HousekeepingServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HousekeepingService implements HousekeepingServiceInt {

    private final HouseKeppingRepo housekeepingRepository;
    private final RoomRepo roomRepository;
    private final EmployeeRepo employeeRepository;

    @Autowired
    public HousekeepingService(HouseKeppingRepo housekeepingRepository, RoomRepo roomRepository, EmployeeRepo employeeRepository) {
        this.housekeepingRepository = housekeepingRepository;
        this.roomRepository = roomRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public APIResponse<List<HousekeepingDto>> getAllHousekeeping() {
        List<Housekeeping> housekeepingList = housekeepingRepository.findAll();

        List<HousekeepingDto> housekeepingDtoList = housekeepingList
                .stream()
                .map(housekeeping -> HousekeepingDto.builder()
                        .id(housekeeping.getId())
                        .roomId(housekeeping.getRoom().getId())
                        .employeeId(housekeeping.getEmployee().getId())
                        .status(housekeeping.getStatus())
                        .taskDate(housekeeping.getTaskDate())
                        .build())
                .toList();

        return APIResponse.ok(housekeepingDtoList, "Housekeeping fetched successfully");

    }

    @Override
    public APIResponse<HousekeepingDto> getHousekeepingById(Long id) {
        Housekeeping housekeeping = housekeepingRepository.findById(id).orElse(null);

        if (housekeeping == null) {
            throw new ResourceNotFoundException(Housekeeping.class, "id", id.toString());
        }

        HousekeepingDto housekeepingDto = HousekeepingDto.builder()
                .id(housekeeping.getId())
                .roomId(housekeeping.getRoom().getId())
                .employeeId(housekeeping.getEmployee().getId())
                .status(housekeeping.getStatus())
                .taskDate(housekeeping.getTaskDate())
                .build();

        return APIResponse.ok(housekeepingDto, "Housekeeping fetched successfully");
    }

    @Override
    public APIResponse<HousekeepingDto> createHousekeeping(HousekeepingDto housekeepingDto) {

        //id should be null when creating a new housekeeping
        if (housekeepingDto.getId() != null) {
            throw new BadRequestException("Id should be null when creating a new housekeeping");
        }

        //check if the room exists
        if (roomRepository.findById(housekeepingDto.getRoomId()).isEmpty()) {
            throw new BadRequestException("Room with id " + housekeepingDto.getRoomId() + " does not exist");
        }

        //check if the employee exists
        if (employeeRepository.findById(housekeepingDto.getEmployeeId()).isEmpty()) {
            throw new BadRequestException("Employee with id " + housekeepingDto.getEmployeeId() + " does not exist");
        }

        Housekeeping housekeeping = Housekeeping.builder()
                .room(roomRepository.findById(housekeepingDto.getRoomId()).get())
                .employee(employeeRepository.findById(housekeepingDto.getEmployeeId()).get())
                .status(housekeepingDto.getStatus())
                .taskDate(housekeepingDto.getTaskDate())
                .build();


        housekeepingRepository.save(housekeeping);

        HousekeepingDto housekeepingDtoResponse = HousekeepingDto.builder()
                .id(housekeeping.getId())
                .roomId(housekeeping.getRoom().getId())
                .employeeId(housekeeping.getEmployee().getId())
                .status(housekeeping.getStatus())
                .taskDate(housekeeping.getTaskDate())
                .build();

        return APIResponse.ok(housekeepingDtoResponse, "Housekeeping created successfully");
    }

    @Override
    public APIResponse<HousekeepingDto> updateHousekeeping(Long id, HousekeepingDto housekeepingDto) {
        Housekeeping housekeeping = housekeepingRepository.findById(id).orElse(null);

        if (housekeeping == null) {
            throw new ResourceNotFoundException(Housekeeping.class, "id", id.toString());
        }

        //check if the room exists
        if (roomRepository.findById(housekeepingDto.getRoomId()).isEmpty()) {
            throw new BadRequestException("Room with id " + housekeepingDto.getRoomId() + " does not exist");
        }

        //check if the employee exists
        if (employeeRepository.findById(housekeepingDto.getEmployeeId()).isEmpty()) {
            throw new BadRequestException("Employee with id " + housekeepingDto.getEmployeeId() + " does not exist");
        }

        housekeeping.setRoom(roomRepository.findById(housekeepingDto.getRoomId()).get());
        housekeeping.setEmployee(employeeRepository.findById(housekeepingDto.getEmployeeId()).get());
        housekeeping.setStatus(housekeepingDto.getStatus());
        housekeeping.setTaskDate(housekeepingDto.getTaskDate());

        housekeepingRepository.save(housekeeping);

        HousekeepingDto housekeepingDtoResponse = HousekeepingDto.builder()
                .id(housekeeping.getId())
                .roomId(housekeeping.getRoom().getId())
                .employeeId(housekeeping.getEmployee().getId())
                .status(housekeeping.getStatus())
                .taskDate(housekeeping.getTaskDate())
                .build();

        return APIResponse.ok(housekeepingDtoResponse, "Housekeeping updated successfully");
    }

    @Override
    public APIResponse<HousekeepingDto> deleteHousekeeping(Long id) {
        Housekeeping housekeeping = housekeepingRepository.findById(id).orElse(null);

        if (housekeeping == null) {
            throw new ResourceNotFoundException(Housekeeping.class, "id", id.toString());
        }

        housekeepingRepository.delete(housekeeping);

        HousekeepingDto housekeepingDto = HousekeepingDto.builder()
                .id(housekeeping.getId())
                .roomId(housekeeping.getRoom().getId())
                .employeeId(housekeeping.getEmployee().getId())
                .status(housekeeping.getStatus())
                .taskDate(housekeeping.getTaskDate())
                .build();

        return APIResponse.ok(housekeepingDto, "Housekeeping deleted successfully");
    }

    @Override
    public APIResponse<List<HousekeepingDto>> searchByParams(Specification<Housekeeping> params) {
        List<Housekeeping> housekeepingList = housekeepingRepository.findAll(params);

        List<HousekeepingDto> housekeepingDtoList = housekeepingList
                .stream()
                .map(housekeeping -> HousekeepingDto.builder()
                        .id(housekeeping.getId())
                        .roomId(housekeeping.getRoom().getId())
                        .employeeId(housekeeping.getEmployee().getId())
                        .status(housekeeping.getStatus())
                        .taskDate(housekeeping.getTaskDate())
                        .build())
                .toList();

        return APIResponse.ok(housekeepingDtoList, "Housekeeping fetched successfully");
    }



}
