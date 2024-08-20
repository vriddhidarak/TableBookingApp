package com.vriddhi.tablebookingapp;

import com.vriddhi.tablebookingapp.model.Restaurant;
import com.vriddhi.tablebookingapp.repository.RestaurantRepository;
import com.vriddhi.tablebookingapp.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantService restaurantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantName("Test Restaurant");

        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);

        Restaurant savedRestaurant = restaurantService.saveRestaurant(restaurant);

        assertNotNull(savedRestaurant);
        assertEquals("Test Restaurant", savedRestaurant.getRestaurantName());
        verify(restaurantRepository, times(1)).save(restaurant);
    }

    @Test
    void testGetRestaurantById() {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(1L);
        restaurant.setRestaurantName("Test Restaurant");

        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));

        Optional<Restaurant> foundRestaurant = restaurantService.getRestaurantById(1L);

        assertTrue(foundRestaurant.isPresent());
        assertEquals("Test Restaurant", foundRestaurant.get().getRestaurantName());
        verify(restaurantRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteRestaurant() {
        doNothing().when(restaurantRepository).deleteById(1L);

        restaurantService.deleteRestaurant(1L);

        verify(restaurantRepository, times(1)).deleteById(1L);
    }
}