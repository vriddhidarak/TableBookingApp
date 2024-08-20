package com.vriddhi.tablebookingapp;

import com.vriddhi.tablebookingapp.model.Restaurant;
import com.vriddhi.tablebookingapp.model.Table;
import com.vriddhi.tablebookingapp.repository.RestaurantRepository;
import com.vriddhi.tablebookingapp.repository.TableRepository;
import com.vriddhi.tablebookingapp.service.TableService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TableServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private TableRepository tableRepository;

    @InjectMocks
    private TableService tableService;

    private Restaurant restaurant;
    private Table table;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        restaurant = new Restaurant(1L, "Restaurant A", "Location A", 10, "Description A", "City A", null, null, null);
        table = new Table(1L, 5, 4, restaurant, null);
    }

    @Test
    void testGetTablesByRestaurantId_Success() {
        // Arrange
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(tableRepository.findByRestaurant(restaurant)).thenReturn(Arrays.asList(table));

        // Act
        List<Table> result = tableService.getTablesByRestaurantId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(restaurantRepository, times(1)).findById(1L);
        verify(tableRepository, times(1)).findByRestaurant(restaurant);
    }

    @Test
    void testGetTablesByRestaurantId_RestaurantNotFound() {
        // Arrange
        when(restaurantRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            tableService.getTablesByRestaurantId(1L);
        });

        assertEquals("Restaurant not found", exception.getMessage());
        verify(restaurantRepository, times(1)).findById(1L);
        verify(tableRepository, times(0)).findByRestaurant(any(Restaurant.class));
    }

    @Test
    void testGetTableById_Success() {
        // Arrange
        when(tableRepository.findById(1L)).thenReturn(Optional.of(table));

        // Act
        Optional<Table> result = tableService.getTableById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(table, result.get());
        verify(tableRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTableById_TableNotFound() {
        // Arrange
        when(tableRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<Table> result = tableService.getTableById(1L);

        // Assert
        assertFalse(result.isPresent());
        verify(tableRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveTable_Success() {
        // Arrange
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(tableRepository.save(table)).thenReturn(table);

        // Act
        Table result = tableService.saveTable(table, 1L);

        // Assert
        assertNotNull(result);
        assertEquals(table, result);
        assertEquals(11, restaurant.getRestaurantTotalTableCount());  // Ensure the table count is updated
        verify(restaurantRepository, times(1)).findById(1L);
        verify(tableRepository, times(1)).save(table);
    }

    @Test
    void testSaveTable_RestaurantNotFound() {
        // Arrange
        when(restaurantRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            tableService.saveTable(table, 1L);
        });

        assertEquals("Restaurant not found", exception.getMessage());
        verify(restaurantRepository, times(1)).findById(1L);
        verify(tableRepository, times(0)).save(any(Table.class));
    }

    @Test
    void testDeleteTable_Success() {
        // Act
        tableService.deleteTable(1L);

        // Assert
        verify(tableRepository, times(1)).deleteById(1L);
    }
}
