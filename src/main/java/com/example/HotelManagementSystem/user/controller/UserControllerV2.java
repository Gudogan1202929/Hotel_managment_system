package com.example.HotelManagementSystem.user.controller;

import com.example.HotelManagementSystem.dto.response.APIResponse;
import com.example.HotelManagementSystem.user.dto.PasswordDto;
import com.example.HotelManagementSystem.user.entity.User;
import com.example.HotelManagementSystem.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "UserV2", description = "All user related endpoints")
@RestController
@RequestMapping("/api/v2/user")
public class UserControllerV2 {

    private final UserService userService;


    @Autowired
    public UserControllerV2(UserService userService) {
        this.userService = userService;
    }

    @DeleteMapping
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Delete user by ID V2", description = "This endpoint is used to delete a user by ID and verify their password")
    @Consumes(MediaType.APPLICATION_JSON)
    public APIResponse<?> deleteUserById(@RequestParam("id") Long id, @RequestBody PasswordDto requestBody) {
        try {
            String password = requestBody.getPassword();
            if (password == null) {
                return APIResponse.badRequest(null,"Password is required");
            }

            User user = userService.getUserById(id, password);

            String result = userService.deleteUserById(id, password);

            if (result.contains("deleted successfully")) {
                return APIResponse.ok(user, result);
            } else if (result.contains("does not exist") || result.contains("incorrect") || result.contains("Cannot delete")) {
                return APIResponse.badRequest(user, result);
            } else {
                return APIResponse.internalServerError(user, result);
            }
        } catch (Exception e) {
            return APIResponse.internalServerError(null, "Error: " + e.getMessage());
        }
    }}
