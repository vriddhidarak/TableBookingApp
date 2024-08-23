package com.vriddhi.tablebookingapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vriddhi.tablebookingapp.config.SecurityConfiguration;
import com.vriddhi.tablebookingapp.controller.UserController;
import com.vriddhi.tablebookingapp.dto.UserResponseDTO;
import com.vriddhi.tablebookingapp.model.User;
import com.vriddhi.tablebookingapp.service.UserServiceInterface;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.security.test.context.support.WithMockUser;

@WebMvcTest(controllers = UserController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfiguration.class))
@Import(MockSecurityConfig.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceInterface userService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithMockUser
    public void testGetUsers() throws Exception {
        Set<UserResponseDTO> users = new HashSet<>();
        UserResponseDTO user = new UserResponseDTO();
        user.setUserId(1L);
        user.setUserName("John Doe");
        users.add(user);

        given(userService.getUsers()).willReturn(users);

        mockMvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(1L))
                .andExpect(jsonPath("$[0].userName").value("John Doe"));
    }

    @Test
    @WithMockUser(roles="USER")
    public void testRegisterUser() throws Exception {
        User user = new User();
        user.setUserName("John Doe");

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setUserId(1L);
        userResponseDTO.setUserName("John Doe");

        given(userService.saveUser(any(User.class))).willReturn(userResponseDTO);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.userName").value("John Doe"));
    }

    @Test
    @WithMockUser
    public void testGetUser() throws Exception {
        UserResponseDTO user = new UserResponseDTO();
        user.setUserId(1L);
        user.setUserName("John Doe");

        given(userService.getUserById(1L)).willReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.userName").value("John Doe"));
    }

    @Test
    @WithMockUser
    public void testUpdateUser() throws Exception {
        User user = new User();
        user.setUserName("John Updated");

        UserResponseDTO updatedUser = new UserResponseDTO();
        updatedUser.setUserId(1L);
        updatedUser.setUserName("John Updated");

        given(userService.saveUser(any(User.class))).willReturn(updatedUser);

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.userName").value("John Updated"));
    }

    @Test
    @WithMockUser
    public void testDeleteUser() throws Exception {
        doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUser(1L);
    }
}