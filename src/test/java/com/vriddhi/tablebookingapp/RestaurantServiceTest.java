package com.vriddhi.tablebookingapp;

import com.vriddhi.tablebookingapp.model.Restaurant;
import com.vriddhi.tablebookingapp.model.Table;
import com.vriddhi.tablebookingapp.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.vriddhi.tablebookingapp.service.RestaurantService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantService restaurantService;

    private Restaurant restaurant;
    private Table table1;
    private Table table2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        table1 = new Table(1L, 5, 4, null, null);
        table2 = new Table(2L, 6, 4, null, null);
        restaurant = new Restaurant(1L, "Restaurant A", "Location A", 0, "Description A", "City A", Arrays.asList(table1, table2), null, null);
    }

    @Test
    void testGetAllRestaurants_Success() {
        // Arrange
        when(restaurantRepository.findAll()).thenReturn(Arrays.asList(restaurant));

        // Act
        List<Restaurant> result = restaurantService.getAllRestaurants();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(2, result.get(0).getRestaurantTotalTableCount());
        verify(restaurantRepository, times(2)).findAll();  // Called twice, once before updating table count and once after
        verify(restaurantRepository, times(1)).save(restaurant);
    }

    @Test
    void testGetRestaurantById_Success() {
        // Arrange
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));

        // Act
        Optional<Restaurant> result = restaurantService.getRestaurantById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(restaurant, result.get());
        assertEquals(2, result.get().getRestaurantTotalTableCount());
        verify(restaurantRepository, times(2)).findById(1L);
        verify(restaurantRepository, times(2)).save(restaurant);
    }

    @Test
    void testGetRestaurantById_NotFound() {
        // Arrange
        when(restaurantRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<Restaurant> result = restaurantService.getRestaurantById(1L);

        // Assert
        assertFalse(result.isPresent());
        verify(restaurantRepository, times(1)).findById(1L);
        verify(restaurantRepository, times(0)).save(any(Restaurant.class));
    }

    @Test
    void testSaveRestaurant_Success() {
        // Arrange
        when(restaurantRepository.save(restaurant)).thenReturn(restaurant);

        // Act
        Restaurant result = restaurantService.saveRestaurant(restaurant);

        // Assert
        assertNotNull(result);
        assertEquals(restaurant, result);
        assertEquals(2, result.getRestaurantTotalTableCount());
        verify(restaurantRepository, times(2)).save(restaurant);
    }

    @Test
    void testDeleteRestaurant_Success() {
        // Act
        restaurantService.deleteRestaurant(1L);

        // Assert
        verify(restaurantRepository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdateRestaurantTotalTableCount_Success() {
        // Act
        restaurantService.saveRestaurant(restaurant);

        // Assert
        assertEquals(2, restaurant.getRestaurantTotalTableCount());
        verify(restaurantRepository, times(2)).save(restaurant);
    }
}
