package com.vriddhi.tablebookingapp.controller;

import com.vriddhi.tablebookingapp.dto.UserResponseDTO;
import com.vriddhi.tablebookingapp.model.User;
import com.vriddhi.tablebookingapp.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServiceInterface userService;

    @GetMapping
    @Operation(summary = "Get all Users", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved Users",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))})
    })
    public ResponseEntity<Set<UserResponseDTO>> getUsers() {
        Set<UserResponseDTO> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    @Operation(summary = "Register User", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully registered User",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody User user) {
        UserResponseDTO newUser = userService.saveUser(user);
        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get User by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved User",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long userId) {
        Optional<UserResponseDTO> user = userService.getUserById(userId);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update User", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully updated User",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long userId, @RequestBody User user) {
        user.setUserId(userId); //wrong logic what if user id passed is not right
        UserResponseDTO updatedUser = userService.saveUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete User", responses = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted User"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
