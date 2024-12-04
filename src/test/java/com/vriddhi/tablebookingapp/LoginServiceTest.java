package com.vriddhi.tablebookingapp;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.vriddhi.tablebookingapp.dto.LoginRequestDTO;
import com.vriddhi.tablebookingapp.dto.LoginResponseDTO;
import com.vriddhi.tablebookingapp.model.User;
import com.vriddhi.tablebookingapp.repository.UserRepository;
import com.vriddhi.tablebookingapp.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

public class LoginServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private LoginService loginService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoginUser_Success() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("user@example.com", "password");
        User user = new User();
        user.setEmail("user@example.com");
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail("user@example.com")).thenReturn(user);
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);

        LoginResponseDTO response = loginService.loginUser(loginRequestDTO);

        assertNotNull(response);
        assertEquals("user@example.com", response.getEmail());
    }

    @Test
    public void testLoginUser_Failed_IncorrectPassword() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("user@example.com", "wrongPassword");
        User user = new User();
        user.setEmail("user@example.com");
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail("user@example.com")).thenReturn(user);
        when(passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            loginService.loginUser(loginRequestDTO);
        });
    }

    @Test
    public void testLoginUser_Failed_UserNotFound() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("nonexistent@example.com", "password");

        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            loginService.loginUser(loginRequestDTO);
        });
    }

    @Test
    public void testLoginUser_NullEmail() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(null, "password");

        assertThrows(IllegalArgumentException.class, () -> {
            loginService.loginUser(loginRequestDTO);
        });
    }

    @Test
    public void testLoginUser_EmptyEmail() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("", "password");

        assertThrows(IllegalArgumentException.class, () -> {
            loginService.loginUser(loginRequestDTO);
        });
    }

    @Test
    public void testLoginUser_NullPassword() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("user@example.com", null);

        assertThrows(IllegalArgumentException.class, () -> {
            loginService.loginUser(loginRequestDTO);
        });
    }

    @Test
    public void testLoginUser_EmptyPassword() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("user@example.com", "");

        assertThrows(IllegalArgumentException.class, () -> {
            loginService.loginUser(loginRequestDTO);
        });
    }
}