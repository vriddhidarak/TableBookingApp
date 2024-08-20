package com.vriddhi.tablebookingapp.service;

import com.vriddhi.tablebookingapp.dto.UserResponseDTO;
import com.vriddhi.tablebookingapp.exception.ResourceNotFoundException;
import com.vriddhi.tablebookingapp.model.User;
import com.vriddhi.tablebookingapp.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService implements UserServiceInterface {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public UserResponseDTO saveUser(User user) {
        log.info("Saving user: {}", user);
        return mapToUserResponseDTO(userRepository.save(user));
    }

    @Override
    public Optional<UserResponseDTO> getUserById(Long userId) {
        log.info("Getting user by ID: {}", userId);
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
        return userRepository.findById(userId).map(this::mapToUserResponseDTO);
    }


    @Override
    @Transactional
    public void deleteUser(Long userId) {
        log.info("Deleting user by ID: {}", userId);
        if(!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
        userRepository.deleteById(userId);
    }

    @Override
    public Set<UserResponseDTO> getUsers() {
        log.info("Getting all users");
        if (userRepository.count() == 0) {
            throw new ResourceNotFoundException("No users found");
        }
        return userRepository.findAll()
                .stream()
                .map(this::mapToUserResponseDTO)
                .collect(Collectors.toSet());
    }

    private UserResponseDTO mapToUserResponseDTO(User user) {
        return new UserResponseDTO(user.getUserId(), user.getUserName(), user.getEmail(), user.getPhone(), user.getUserAddress());
    }
}
