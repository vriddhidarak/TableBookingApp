package com.vriddhi.tablebookingapp.service;

import com.vriddhi.tablebookingapp.dto.LoginRequestDTO;
import com.vriddhi.tablebookingapp.dto.UserResponseDTO;
import com.vriddhi.tablebookingapp.model.User;
import com.vriddhi.tablebookingapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Transactional
    public UserResponseDTO saveUser(User user) {
        return mapToUserResponseDTO(userRepository.save(user));
    }

    public Optional<UserResponseDTO> getUserById(Long userId) {
        return userRepository.findById(userId).map(this::mapToUserResponseDTO);
    }

    public UserResponseDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("User with email " + email + " not found.");
        }
        return mapToUserResponseDTO(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public List<UserResponseDTO> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToUserResponseDTO)
                .collect(Collectors.toList());
    }

    private UserResponseDTO mapToUserResponseDTO(User user) {
        return new UserResponseDTO(user.getUserId(), user.getUserName(), user.getEmail(), user.getPhone(), user.getUserAddress());
    }
}
