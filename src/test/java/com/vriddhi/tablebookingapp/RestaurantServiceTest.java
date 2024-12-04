package com.vriddhi.tablebookingapp;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.vriddhi.tablebookingapp.model.Restaurant;
import com.vriddhi.tablebookingapp.repository.RestaurantRepository;
import com.vriddhi.tablebookingapp.service.RestaurantService;
import com.vriddhi.tablebookingapp.dto.RestaurantResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantService restaurantService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllRestaurants_Success() {
        when(restaurantRepository.findAll()).thenReturn(List.of(new Restaurant(1L, "Test Restaurant")));
        List<RestaurantResponseDTO> restaurants = restaurantService.getAllRestaurants();
        assertNotNull(restaurants);
        assertEquals(1, restaurants.size());
        assertEquals("Test Restaurant", restaurants.get(0).getRestaurantName());
    }

    @Test
    public void testGetAllRestaurants_Empty() {
        when(restaurantRepository.findAll()).thenReturn(List.of());
        List<RestaurantResponseDTO> restaurants = restaurantService.getAllRestaurants();
        assertTrue(restaurants.isEmpty());
    }

    @Test
    public void testGetRestaurantById_Found() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(new Restaurant(1L, "Test Restaurant")));
        Optional<RestaurantResponseDTO> restaurant = restaurantService.getRestaurantById(1L);
        assertTrue(restaurant.isPresent());
        assertEquals("Test Restaurant", restaurant.get().getRestaurantName());
    }

    @Test
    public void testGetRestaurantById_NotFound() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<RestaurantResponseDTO> restaurant = restaurantService.getRestaurantById(1L);
        assertFalse(restaurant.isPresent());
    }

    @Test
    public void testSaveRestaurant_Success() {
        Restaurant newRestaurant = new Restaurant(1L, "New Restaurant");
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(newRestaurant);
        RestaurantResponseDTO savedRestaurant = restaurantService.saveRestaurant(newRestaurant);
        assertNotNull(savedRestaurant);
        assertEquals("New Restaurant", savedRestaurant.getRestaurantName());
    }

    @Test
    public void testDeleteRestaurant_Success() {
        doNothing().when(restaurantRepository).deleteById(1L);
        restaurantService.deleteRestaurant(1L);
        verify(restaurantRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testSaveRestaurant_VerifyMapping() {
        Restaurant restaurantToSave = new Restaurant(0, "New Restaurant");
        Restaurant savedRestaurant = new Restaurant(1L, "New Restaurant");
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(savedRestaurant);

        RestaurantResponseDTO result = restaurantService.saveRestaurant(restaurantToSave);

        ArgumentCaptor<Restaurant> restaurantCaptor = ArgumentCaptor.forClass(Restaurant.class);
        verify(restaurantRepository).save(restaurantCaptor.capture());
        Restaurant capturedRestaurant = restaurantCaptor.getValue();

        assertNotNull(capturedRestaurant);
        assertEquals("New Restaurant", capturedRestaurant.getRestaurantName());
        assertEquals(1L, result.getRestaurantId());
    }

    @Test
    public void testGetRestaurantById_NullInput() {
        assertThrows(IllegalArgumentException.class, () -> {
            restaurantService.getRestaurantById(null).orElseThrow(IllegalArgumentException::new);
        });
    }


    @Test
    public void testSaveRestaurant_NullInput() {
        assertThrows(IllegalArgumentException.class, () -> {
            restaurantService.saveRestaurant(null);
        });
    }
}