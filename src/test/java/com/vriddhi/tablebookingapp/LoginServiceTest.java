package com.vriddhi.tablebookingapp;
import com.vriddhi.tablebookingapp.dto.LoginRequestDTO;
import com.vriddhi.tablebookingapp.dto.UserResponseDTO;
import com.vriddhi.tablebookingapp.model.User;
import com.vriddhi.tablebookingapp.repository.UserRepository;
import com.vriddhi.tablebookingapp.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LoginService loginService;

    private User user;
    private LoginRequestDTO validLoginRequest;
    private LoginRequestDTO invalidEmailLoginRequest;
    private LoginRequestDTO invalidPasswordLoginRequest;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        user = new User(1L, "John Doe", "john.doe@example.com", "password123", "1234567890", "123 Main St", null, null);
        validLoginRequest = new LoginRequestDTO("john.doe@example.com", "password123");
        invalidEmailLoginRequest = new LoginRequestDTO("invalid.email@example.com", "password123");
        invalidPasswordLoginRequest = new LoginRequestDTO("john.doe@example.com", "wrongpassword");
    }

    @Test
    void testLoginUser_ValidCredentials() {
        when(userRepository.findByEmail(anyString())).thenReturn(user);

        UserResponseDTO response = loginService.loginUser(validLoginRequest);
        assertNotNull(response);
        assertEquals(user.getUserId(), response.getUserId());
        verify(userRepository, times(1)).findByEmail(validLoginRequest.getEmail());
    }

    @Test
    void testLoginUser_InvalidEmail() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        assertThrows(IllegalArgumentException.class, () -> loginService.loginUser(invalidEmailLoginRequest));
        verify(userRepository, times(1)).findByEmail(invalidEmailLoginRequest.getEmail());
    }

    @Test
    void testLoginUser_InvalidPassword() {
        when(userRepository.findByEmail(anyString())).thenReturn(user);
        assertThrows(IllegalArgumentException.class, () -> loginService.loginUser(invalidPasswordLoginRequest));
        verify(userRepository, times(1)).findByEmail(invalidPasswordLoginRequest.getEmail());
    }
}