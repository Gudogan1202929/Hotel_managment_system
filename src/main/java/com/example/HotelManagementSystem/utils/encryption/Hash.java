package com.example.HotelManagementSystem.utils.encryption;

import com.example.HotelManagementSystem.utils.constant.SystemConstants;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {

    public static String hashing(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(SystemConstants.TYPE_HASHING);
        byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
