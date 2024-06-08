package com.example.HotelManagementSystem.user.service;

import com.example.HotelManagementSystem.user.entity.LogFile;
import com.example.HotelManagementSystem.user.repositorie.LogFileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogFileService {

    private final LogFileRepo logFileRepo;

    @Autowired
    public LogFileService(LogFileRepo logFileRepo) {
        this.logFileRepo = logFileRepo;
    }

    public void saveLog(LogFile logFile) {
        logFileRepo.save(logFile);
    }
}
