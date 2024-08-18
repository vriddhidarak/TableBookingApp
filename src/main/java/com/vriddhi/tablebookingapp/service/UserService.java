package com.vriddhi.tablebookingapp.service;

import com.vriddhi.tablebookingapp.dto.LoginRequestDTO;
import com.vriddhi.tablebookingapp.dto.UserResponseDTO;
import com.vriddhi.tablebookingapp.model.User;
import com.vriddhi.tablebookingapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public static UserResponseDTO getUserResponseDTO(User user) {
        return new UserResponseDTO(user.getUserId(), user.getUserName(), user.getEmail(), user.getPhone(), user.getUserAddress());
    }

    public UserResponseDTO loginUser(LoginRequestDTO loginRequestDTO) {
        String email = loginRequestDTO.getEmail();
        String password = loginRequestDTO.getPassword();
        User user = userRepository.findByEmail(email);
        System.out.println("User: " + user);
        if (user != null && user.getPassword().equals(password)) {
            System.out.println("User logged in successfully");
            return getUserResponseDTO(user);
        }
        return null;
    }

    public UserResponseDTO saveUser(User user) {

        return getUserResponseDTO(userRepository.save(user));
    }

    public Optional<UserResponseDTO> getUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(UserService::getUserResponseDTO);
    }

    public UserResponseDTO getUserByEmail(String email) {

        return getUserResponseDTO(userRepository.findByEmail(email));
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public Iterable<UserResponseDTO> getUsers() {
        return userRepository.findAll()
                .stream().map(UserService::getUserResponseDTO).toList();
    }
}
