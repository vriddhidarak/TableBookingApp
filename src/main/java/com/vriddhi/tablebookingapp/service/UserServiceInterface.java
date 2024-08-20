package com.vriddhi.tablebookingapp.service;

import com.vriddhi.tablebookingapp.dto.UserResponseDTO;
import com.vriddhi.tablebookingapp.model.User;

import java.util.Optional;
import java.util.Set;

public interface UserServiceInterface {
    UserResponseDTO saveUser(User user);
    Optional<UserResponseDTO> getUserById(Long userId);
    void deleteUser(Long userId);
    Set<UserResponseDTO> getUsers();
}
