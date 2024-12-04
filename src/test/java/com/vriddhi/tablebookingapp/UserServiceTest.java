package com.vriddhi.tablebookingapp;

import com.vriddhi.tablebookingapp.dto.UserResponseDTO;
import com.vriddhi.tablebookingapp.exception.ResourceNotFoundException;
import com.vriddhi.tablebookingapp.model.User;
import com.vriddhi.tablebookingapp.repository.UserRepository;
import com.vriddhi.tablebookingapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1L, "John Doe", "john.doe@example.com", "password123", "1234567890", "123 Main St");
    }

    @Test
    void testSaveUser() {
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        UserResponseDTO response = userService.saveUser(user);
        assertNotNull(response);
        assertEquals(user.getUserId(), response.getUserId());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testGetUserById_UserExists() {
        when(userRepository.existsById(user.getUserId())).thenReturn(true);
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
        Optional<UserResponseDTO> response = userService.getUserById(user.getUserId());
        assertTrue(response.isPresent());
        assertEquals(user.getUserId(), response.get().getUserId());
        verify(userRepository, times(1)).existsById(user.getUserId());
        verify(userRepository, times(1)).findById(user.getUserId());
    }

    @Test
    void testGetUserById_UserDoesNotExist() {
        when(userRepository.existsById(user.getUserId())).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(user.getUserId()));
        verify(userRepository, times(1)).existsById(user.getUserId());
        verify(userRepository, times(0)).findById(user.getUserId());
    }

    @Test
    void testDeleteUser_UserExists() {
        when(userRepository.existsById(user.getUserId())).thenReturn(true);
        doNothing().when(userRepository).deleteById(user.getUserId());
        userService.deleteUser(user.getUserId());
        verify(userRepository, times(1)).existsById(user.getUserId());
        verify(userRepository, times(1)).deleteById(user.getUserId());
    }

    @Test
    void testDeleteUser_UserDoesNotExist() {
        when(userRepository.existsById(user.getUserId())).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(user.getUserId()));
        verify(userRepository, times(1)).existsById(user.getUserId());
        verify(userRepository, times(0)).deleteById(user.getUserId());
    }

    @Test
    void testGetUsers_UsersExist() {
        when(userRepository.count()).thenReturn(1L);
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        Set<UserResponseDTO> response = userService.getUsers();
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
        verify(userRepository, times(1)).count();
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUsers_NoUsersExist() {
        when(userRepository.count()).thenReturn(0L);
        assertThrows(ResourceNotFoundException.class, () -> userService.getUsers());
        verify(userRepository, times(1)).count();
        verify(userRepository, times(0)).findAll();
    }
}