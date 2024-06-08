package com.example.HotelManagementSystem.utils.encryption;

import com.example.HotelManagementSystem.utils.constant.SystemConstants;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Encryption {
    public static String Decrypt(String password) throws Exception {
        try {
            String keyText = "GXBD91tMbO47qkExaDLuOVU6K4fEu0V8"; // Replace with your AES-256 key
            String ivText = "MyIV012345678910"; // Replace with your initialization vector (IV)

            byte[] encryptedBytes = Base64.getDecoder().decode(password.getBytes(StandardCharsets.UTF_8));
            byte[] keyBytes = keyText.getBytes("UTF-8");
            byte[] ivBytes = ivText.getBytes("UTF-8");

            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            String decryptedText = new String(decryptedBytes, "UTF-8");

            return decryptedText;
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
            throw new Exception(SystemConstants.CANNT_LOGIN);
        }
    }

    public static String Encrypt(String plaintext) throws Exception {
        try {

            String KEY_TEXT = "GXBD91tMbO47qkExaDLuOVU6K4fEu0V8"; // Replace with your AES-256 key
            String IV_TEXT = "MyIV012345678910";


            byte[] keyBytes = KEY_TEXT.getBytes("UTF-8");
            byte[] ivBytes = IV_TEXT.getBytes("UTF-8");

            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

            byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error while encrypting: " + e.toString());
        }
    }
}
