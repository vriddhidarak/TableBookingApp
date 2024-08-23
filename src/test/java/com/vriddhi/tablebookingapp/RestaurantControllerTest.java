package com.vriddhi.tablebookingapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vriddhi.tablebookingapp.config.SecurityConfiguration;
import com.vriddhi.tablebookingapp.controller.RestaurantController;
import com.vriddhi.tablebookingapp.dto.RestaurantResponseDTO;
import com.vriddhi.tablebookingapp.model.Restaurant;
import com.vriddhi.tablebookingapp.service.RestaurantServiceInterface;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = RestaurantController.class,excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfiguration.class))
@Import(MockSecurityConfig.class)
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantServiceInterface restaurantService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGetAllRestaurants() throws Exception {
        RestaurantResponseDTO restaurant1 = new RestaurantResponseDTO(1L, "Restaurant1", "Location1", "Description1", 5, null, null, null);
        RestaurantResponseDTO restaurant2 = new RestaurantResponseDTO(2L, "Restaurant2", "Location2", "Description2", 10, null, null, null);

        given(restaurantService.getAllRestaurants()).willReturn(Arrays.asList(restaurant1, restaurant2));

        mockMvc.perform(get("/api/restaurants")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].restaurantId").value(1L))
                .andExpect(jsonPath("$[0].restaurantName").value("Restaurant1"))
                .andExpect(jsonPath("$[1].restaurantId").value(2L))
                .andExpect(jsonPath("$[1].restaurantName").value("Restaurant2"));
    }

    @Test
    public void testGetRestaurant() throws Exception {
        RestaurantResponseDTO restaurant = new RestaurantResponseDTO(1L, "Restaurant1", "Location1", "Description1", 5, null, null, null);

        given(restaurantService.getRestaurantById(1L)).willReturn(Optional.of(restaurant));

        mockMvc.perform(get("/api/restaurants/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.restaurantId").value(1L))
                .andExpect(jsonPath("$.restaurantName").value("Restaurant1"));
    }

    @Test
    public void testCreateRestaurant() throws Exception {
        Restaurant restaurant = new Restaurant(1L, "New Restaurant", "New Location", 0, "New Description", "New City", null, null, null);
        RestaurantResponseDTO newRestaurant = new RestaurantResponseDTO(1L, "New Restaurant", "New Location", "New Description", 0, null, null, null);

        given(restaurantService.saveRestaurant(any(Restaurant.class))).willReturn(newRestaurant);

        mockMvc.perform(post("/api/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restaurant)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.restaurantId").value(1L))
                .andExpect(jsonPath("$.restaurantName").value("New Restaurant"));
    }

    @Test
    public void testUpdateRestaurant() throws Exception {
        Restaurant updatedInfo = new Restaurant(1L, "Updated Restaurant", "Updated Location", 0, "Updated Description", "Updated City", null, null, null);
        RestaurantResponseDTO updatedRestaurant = new RestaurantResponseDTO(1L, "Updated Restaurant", "Updated Location", "Updated Description", 0, null, null, null);

        given(restaurantService.saveRestaurant(any(Restaurant.class))).willReturn(updatedRestaurant);

        mockMvc.perform(put("/api/restaurants/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedInfo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.restaurantId").value(1L))
                .andExpect(jsonPath("$.restaurantName").value("Updated Restaurant"));
    }

    @Test
    public void testDeleteRestaurant() throws Exception {
        doNothing().when(restaurantService).deleteRestaurant(1L);

        mockMvc.perform(delete("/api/restaurants/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}