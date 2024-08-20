package com.vriddhi.tablebookingapp;

import com.vriddhi.tablebookingapp.dto.UserResponseDTO;
import com.vriddhi.tablebookingapp.model.User;
import com.vriddhi.tablebookingapp.repository.UserRepository;
import com.vriddhi.tablebookingapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;
import java.util.Arrays;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUser() {


        User user = new User(1L, "John Doe", "test@example.com", "password123", "1234567890", "123 Street");
        when(userRepository.save(user)).thenReturn(user);


        UserResponseDTO result = userService.saveUser(user);


        assertNotNull(result);
        assertEquals("John Doe", result.getUserName());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testGetUserById() {

        User user = new User(1L, "John Doe", "test@example.com", "password123", "1234567890", "123 Street");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));


        Optional<UserResponseDTO> result = userService.getUserById(1L);


        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getUserName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteUser() {

        userService.deleteUser(1L);


        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetUsers() {

        User user1 = new User(1L, "John Doe", "john@example.com", "password123", "1234567890", "123 Street");
        User user2 = new User(2L, "Jane Doe", "jane@example.com", "password123", "0987654321", "456 Avenue");
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));


        Set<UserResponseDTO> result = userService.getUsers();


        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }
}
