package com.vriddhi.tablebookingapp.service;

import com.vriddhi.tablebookingapp.dto.LoginRequestDTO;
import com.vriddhi.tablebookingapp.dto.UserResponseDTO;
import com.vriddhi.tablebookingapp.repository.UserRepository;
import com.vriddhi.tablebookingapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginService {

    @Autowired
    private UserRepository userRepository;

    public UserResponseDTO loginUser(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findByEmail(loginRequestDTO.getEmail());

        if (user != null && user.getPassword().equals(loginRequestDTO.getPassword())) {
            return mapToUserResponseDTO(user);
        }

        return null;
    }

    private UserResponseDTO mapToUserResponseDTO(User user) {
        return new UserResponseDTO(user.getUserId(), user.getUserName(), user.getEmail(), user.getPhone(), user.getUserAddress());
    }
}
