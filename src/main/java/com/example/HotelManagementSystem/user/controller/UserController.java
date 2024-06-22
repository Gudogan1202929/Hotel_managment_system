package com.example.HotelManagementSystem.user.controller;

import com.example.HotelManagementSystem.user.dto.ChangeRole;
import com.example.HotelManagementSystem.user.dto.ChangeUserName;
import com.example.HotelManagementSystem.user.dto.ChangeUserPassword;
import com.example.HotelManagementSystem.user.dto.PasswordDto;
import com.example.HotelManagementSystem.user.entity.User;
import com.example.HotelManagementSystem.user.jwt.Create;
import com.example.HotelManagementSystem.user.service.UserService;
import com.example.HotelManagementSystem.utils.constant.SystemPaths;
import com.example.HotelManagementSystem.utils.encryption.Encryption;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "All user related endpoints")
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController (UserService userService,Create create){
        this.userService = userService;
    }

    @PostMapping(SystemPaths.LOGIN_PATH)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "User sign in", description = "This endpoint is used to sign in user")
    public ResponseEntity<?> UserSignIn(@RequestBody User user) {
        try {
            String text = userService.UserSignIn(user);
            if (text.length() < 25) {
                return ResponseEntity.badRequest().body(text);
            }
            return ResponseEntity.ok().body(text);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping(SystemPaths.SIGNUP)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "User sign up", description = "This endpoint is used to sign up user")
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<?> UserSignUp(@RequestBody User user) {

        try {
            System.out.println(Encryption.Encrypt("12345678"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            if (userService.UserSignUp(user) == null) {
                return ResponseEntity.badRequest().body("User with email" + user.getUsername() + " already exists");
            }
            return ResponseEntity.ok().body("User with email" + user.getUsername()  + " has been created");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping(SystemPaths.CHANGEPASSWORD)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "User change password", description = "This endpoint is used to change password of user")
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<?> UserChangePassword(@RequestBody ChangeUserPassword changeUserPassword) {
        try {
            String s = userService.UserChangePassword(changeUserPassword);
            if (s.contains("changed password successfully")){
                return ResponseEntity.ok().body("User with email" + changeUserPassword.getUsername()  + " Changed password successfully");
            }else {
                return ResponseEntity.badRequest().body(s);
            }

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping(SystemPaths.CHANGEUSERNAME)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "User change username", description = "This endpoint is used to change username of user")
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<?> UserChangeUserName(@RequestBody ChangeUserName changeUserName) {
        try {
            String s = userService.UserChangeUserName(changeUserName);
            if (s.contains("successfully")){
                return ResponseEntity.ok().body(s);
            }else {
                return ResponseEntity.badRequest().body(s);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping(SystemPaths.CHANGEROLE)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "User change role", description = "This endpoint is used to change role of user")
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<?> UserChangeRole(@RequestBody ChangeRole changeRole) {
        try {
            String s = userService.UserChangeRole(changeRole);
            if (s.contains("successfully")){
                return ResponseEntity.ok().body("changed the role of user" + changeRole.getUsername() + " successfully to role " + changeRole.getNewRole());
            }else {
                return ResponseEntity.badRequest().body(s);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/user")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get user by ID", description = "This endpoint is used to retrieve a user by ID and verify their password")
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<?> getUserById(@RequestParam("id") Long id, @RequestBody PasswordDto requestBody) {
        try {
            String password = requestBody.getPassword();
            if (password == null) {
                return ResponseEntity.badRequest().body("Password is required");
            }

            User user = userService.getUserById(id, password);
            if (user != null) {
                return ResponseEntity.ok().body(user + " retrieved successfully");
            } else {
                return ResponseEntity.badRequest().body(user);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }


    @DeleteMapping("/user")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Delete user by ID", description = "This endpoint is used to delete a user by ID and verify their password")
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<?> deleteUserById(@RequestParam("id") Long id, @RequestBody PasswordDto requestBody) {
        try {
            String password = requestBody.getPassword();
            if (password == null) {
                return ResponseEntity.badRequest().body("Password is required");
            }

            String result = userService.deleteUserById(id, password);
            if (result.contains("deleted successfully")) {
                return ResponseEntity.ok().body(result);
            } else if (result.contains("does not exist") || result.contains("incorrect") || result.contains("Cannot delete")) {
                return ResponseEntity.badRequest().body(result);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
