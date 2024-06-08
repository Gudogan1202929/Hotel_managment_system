package com.example.HotelManagementSystem.user.service;

import com.example.HotelManagementSystem.user.entity.BlackList;
import com.example.HotelManagementSystem.user.repositorie.BlackListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlackListService {

    private final BlackListRepo blackListRepo;

    @Autowired
    public BlackListService(BlackListRepo blackListRepo) {
        this.blackListRepo = blackListRepo;
    }

    public boolean findByToken(String token) {
        return blackListRepo.findByToken(token) != null;
    }

    public void save(BlackList blackList) {
        blackListRepo.save(blackList);
    }
}
