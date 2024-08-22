package com.vriddhi.tablebookingapp;

import com.vriddhi.tablebookingapp.model.Restaurant;
import com.vriddhi.tablebookingapp.repository.RestaurantRepository;
import com.vriddhi.tablebookingapp.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantService restaurantService;

    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant(1L, "Test Restaurant", "Test Location", 0, "Test Description", "Test City", null, null, null);
    }

    @Test
    void testGetAllRestaurants() {
        when(restaurantRepository.findAll()).thenReturn(Collections.singletonList(restaurant));
        List<Restaurant> response = restaurantService.getAllRestaurants();
        assertNotNull(response);
        assertEquals(1, response.size());
        verify(restaurantRepository, times(1)).findAll();
    }

    @Test
    void testGetRestaurantById_RestaurantExists() {
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));
        Optional<Restaurant> response = restaurantService.getRestaurantById(restaurant.getRestaurantId());
        assertTrue(response.isPresent());
        assertEquals(restaurant.getRestaurantId(), response.get().getRestaurantId());
        verify(restaurantRepository, times(1)).findById(restaurant.getRestaurantId());
    }

    @Test
    void testGetRestaurantById_RestaurantDoesNotExist() {
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.empty());
        Optional<Restaurant> response = restaurantService.getRestaurantById(restaurant.getRestaurantId());
        assertFalse(response.isPresent());
        verify(restaurantRepository, times(1)).findById(restaurant.getRestaurantId());
    }

    @Test
    void testSaveRestaurant() {
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);
        Restaurant response = restaurantService.saveRestaurant(restaurant);
        assertNotNull(response);
        assertEquals(restaurant.getRestaurantId(), response.getRestaurantId());
        verify(restaurantRepository, times(1)).save(restaurant);
    }

    @Test
    void testDeleteRestaurant_RestaurantExists() {
        doNothing().when(restaurantRepository).deleteById(anyLong());
        restaurantService.deleteRestaurant(restaurant.getRestaurantId());
        verify(restaurantRepository, times(1)).deleteById(restaurant.getRestaurantId());
    }

    @Test
    void testDeleteRestaurant_RestaurantDoesNotExist() {
        doThrow(new IllegalArgumentException("Restaurant not found")).when(restaurantRepository).deleteById(anyLong());
        assertThrows(IllegalArgumentException.class, () -> restaurantService.deleteRestaurant(restaurant.getRestaurantId()));
        verify(restaurantRepository, times(1)).deleteById(restaurant.getRestaurantId());
    }
}