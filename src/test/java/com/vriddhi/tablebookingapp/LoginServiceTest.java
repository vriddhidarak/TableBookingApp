package com.vriddhi.tablebookingapp;

import com.vriddhi.tablebookingapp.dto.LoginRequestDTO;
import com.vriddhi.tablebookingapp.dto.UserResponseDTO;
import com.vriddhi.tablebookingapp.model.User;
import com.vriddhi.tablebookingapp.repository.UserRepository;
import com.vriddhi.tablebookingapp.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class LoginServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LoginService loginService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginUser_Success() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("test@example.com", "password123");
        User user = new User(1L, "John Doe", "test@example.com", "password123", "1234567890", "123 Street");
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);


        UserResponseDTO result = loginService.loginUser(loginRequestDTO);


        assertNotNull(result);
        assertEquals("John Doe", result.getUserName());
        verify(userRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void testLoginUser_Failure() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("test@example.com", "wrongpassword");
        User user = new User(1L, "John Doe", "test@example.com", "password123", "1234567890", "123 Street");
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);


        UserResponseDTO result = loginService.loginUser(loginRequestDTO);


        assertNull(result);
        verify(userRepository, times(1)).findByEmail("test@example.com");
    }
}
