package com.vriddhi.tablebookingapp.controller;

import com.vriddhi.tablebookingapp.dto.LoginRequestDTO;
import com.vriddhi.tablebookingapp.dto.UserResponseDTO;
import com.vriddhi.tablebookingapp.model.User;
import com.vriddhi.tablebookingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Iterable<UserResponseDTO>> getUsers() {
        Iterable<UserResponseDTO> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping(path="/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody User user) {
        UserResponseDTO newUser = userService.saveUser(user);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> loginUser(@RequestBody LoginRequestDTO loginRequestDTO) {
        UserResponseDTO user = userService.loginUser(loginRequestDTO);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(401).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long userId) {
        Optional<UserResponseDTO> user = userService.getUserById(userId);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long userId, @RequestBody User user) {
        user.setUserId(userId);
        UserResponseDTO updatedUser = userService.saveUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
