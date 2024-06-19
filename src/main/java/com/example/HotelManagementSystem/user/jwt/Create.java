package com.example.HotelManagementSystem.user.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.HotelManagementSystem.user.entity.BlackList;
import com.example.HotelManagementSystem.user.entity.User;
import com.example.HotelManagementSystem.user.repositorie.UserRepo;
import com.example.HotelManagementSystem.user.service.BlackListService;
import com.example.HotelManagementSystem.user.service.UserService;
import com.example.HotelManagementSystem.utils.constant.SystemConstants;
import com.example.HotelManagementSystem.utils.encryption.Encryption;
import com.example.HotelManagementSystem.utils.encryption.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Create {

    private final UserRepo userRepo;

    private final BlackListService blackListService;

    @Autowired
    public Create(BlackListService blackListService , UserRepo userRepo) {
        this.blackListService = blackListService;
        this.userRepo = userRepo;
    }

    public String createJWT(User user) throws Exception {
try {
    User userModel = userRepo.findByUsername(user.getUsername());

    if (userModel == null || userModel.getUsername() == null){
        return "The user does not exist";
    }

    String password = Encryption.Decrypt(user.getPassword());
    String HashedPassword = Hash.hashing(password);
    if (!HashedPassword.equals(userModel.getPassword())) {
        return "The password is incorrect";
    }

    if (userModel.getToken() != null) {
        BlackList blackListModel = new BlackList(userModel.getToken());
        blackListService.save(blackListModel);
    }

    Algorithm algorithm = Algorithm.HMAC256(SystemConstants.THE_KEY_FOR_TOKEN.getBytes());

    String token = JWT.create()
            .withIssuer(SystemConstants.ISSUER)
            .withSubject(user.getUsername())
            .withClaim(SystemConstants.ROLE, userModel.getRole().name())
            .withExpiresAt(new Date(System.currentTimeMillis() + 3600000))
            .sign(algorithm);

    AddToken(userModel, token);
    return token;
}catch (Exception e) {
    System.out.println("Error: " + e.getMessage());
}
       return null;
    }

    public void AddToken(User user, String token) {
        user.setToken(token);
        userRepo.save(user);
    }

    public User UserSignUp(User user) throws Exception {
        String password = Encryption.Decrypt(user.getPassword());
        user.setPassword(Hash.hashing(password));
        return userRepo.save(user);
    }
}
