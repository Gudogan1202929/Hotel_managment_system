package com.example.HotelManagementSystem.user.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.HotelManagementSystem.user.entity.User;
import com.example.HotelManagementSystem.user.repositorie.UserRepo;
import com.example.HotelManagementSystem.user.service.BlackListService;
import com.example.HotelManagementSystem.user.service.UserService;
import com.example.HotelManagementSystem.utils.constant.SystemConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Check {

    private final UserRepo userRepo;

    private final BlackListService blackListRepo;

    @Autowired
    public Check(UserRepo userRepo,BlackListService blackListRepo){
        this.userRepo = userRepo;
        this.blackListRepo = blackListRepo;
    }

    public boolean CheckJWTIfForUser(String jwt) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SystemConstants.THE_KEY_FOR_TOKEN.getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(jwt);

            User userModelFromDataBase =  userRepo.findByUsername(decodedJWT.getSubject());

            if (!decodedJWT.getSubject().equals(userModelFromDataBase.getUsername()) || blackListRepo.findByToken(jwt)
                    || !jwt.equals(userModelFromDataBase.getToken())){
                return false;
            }
            Long expirationTime = decodedJWT.getClaim(SystemConstants.EXPIRED).asLong();
            long currentTime = System.currentTimeMillis() / 1000;
            return expirationTime > currentTime;
        }catch (Exception e){
            return false;
        }
    }

    public static String WhatRole(String jwt) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SystemConstants.THE_KEY_FOR_TOKEN.getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(jwt);
            return decodedJWT.getClaims().get(SystemConstants.ROLE).asString();
        }catch (Exception e){
            return null;
        }
    }

    public User CheckIfUserExists(String username) {
        return userRepo.findByUsername(username);
    }
}
