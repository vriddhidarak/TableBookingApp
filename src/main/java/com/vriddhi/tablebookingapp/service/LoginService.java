package com.vriddhi.tablebookingapp.service;

import com.vriddhi.tablebookingapp.dto.LoginRequestDTO;
import com.vriddhi.tablebookingapp.dto.LoginResponseDTO;
import com.vriddhi.tablebookingapp.dto.UserResponseDTO;
import com.vriddhi.tablebookingapp.repository.UserRepository;
import com.vriddhi.tablebookingapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.vriddhi.tablebookingapp.dto.LoginResponseDTO.mapToLoginResponseDTO;
import static com.vriddhi.tablebookingapp.dto.UserResponseDTO.mapToUserResponseDTO;

@Service
public class LoginService implements LoginServiceInterface {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public LoginResponseDTO loginUser(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findByEmail(loginRequestDTO.getEmail());
        if (user != null && passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            return mapToLoginResponseDTO(user);
        }
        else {
            throw new IllegalArgumentException("Invalid email or password");
        }
    }

}
