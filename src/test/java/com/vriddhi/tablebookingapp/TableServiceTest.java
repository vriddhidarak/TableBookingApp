package com.vriddhi.tablebookingapp;

import com.vriddhi.tablebookingapp.model.Restaurant;
import com.vriddhi.tablebookingapp.model.Table;
import com.vriddhi.tablebookingapp.repository.RestaurantRepository;
import com.vriddhi.tablebookingapp.repository.TableRepository;
import com.vriddhi.tablebookingapp.service.TableService;
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
public class TableServiceTest {

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
        restaurant = new Restaurant(1L, "Test Restaurant", "Test Location", 0, "Test Description", "Test City", null, null, null);
        table = new Table(1L,4, 4, restaurant);
    }

    @Test
    void testGetTablesByRestaurantId_RestaurantExists() {
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));
        when(tableRepository.findByRestaurant(any(Restaurant.class))).thenReturn(Collections.singletonList(table));
        List<Table> response = tableService.getTablesByRestaurantId(restaurant.getRestaurantId());
        assertNotNull(response);
        assertEquals(1, response.size());
        verify(restaurantRepository, times(1)).findById(restaurant.getRestaurantId());
        verify(tableRepository, times(1)).findByRestaurant(restaurant);
    }

    @Test
    void testGetTablesByRestaurantId_RestaurantDoesNotExist() {
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> tableService.getTablesByRestaurantId(restaurant.getRestaurantId()));
        verify(restaurantRepository, times(1)).findById(restaurant.getRestaurantId());
        verify(tableRepository, times(0)).findByRestaurant(any(Restaurant.class));
    }

    @Test
    void testGetTableById_TableExists() {
        when(tableRepository.findById(anyLong())).thenReturn(Optional.of(table));
        Optional<Table> response = tableService.getTableById(table.getTableId());
        assertTrue(response.isPresent());
        assertEquals(table.getTableId(), response.get().getTableId());
        verify(tableRepository, times(1)).findById(table.getTableId());
    }

    @Test
    void testGetTableById_TableDoesNotExist() {
        when(tableRepository.findById(anyLong())).thenReturn(Optional.empty());
        Optional<Table> response = tableService.getTableById(table.getTableId());
        assertFalse(response.isPresent());
        verify(tableRepository, times(1)).findById(table.getTableId());
    }

    @Test
    void testSaveTable_RestaurantExists() {
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));
        when(tableRepository.save(any(Table.class))).thenReturn(table);
        Table response = tableService.saveTable(table, restaurant.getRestaurantId());
        assertNotNull(response);
        assertEquals(table.getTableId(), response.getTableId());
        verify(restaurantRepository, times(1)).findById(restaurant.getRestaurantId());
        verify(tableRepository, times(1)).save(table);
    }

    @Test
    void testSaveTable_RestaurantDoesNotExist() {
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> tableService.saveTable(table, restaurant.getRestaurantId()));
        verify(restaurantRepository, times(1)).findById(restaurant.getRestaurantId());
        verify(tableRepository, times(0)).save(any(Table.class));
    }

    @Test
    void testDeleteTable_TableExists() {
        doNothing().when(tableRepository).deleteById(anyLong());
        tableService.deleteTable(table.getTableId());
        verify(tableRepository, times(1)).deleteById(table.getTableId());
    }

    @Test
    void testDeleteTable_TableDoesNotExist() {
        doThrow(new IllegalArgumentException("Table not found")).when(tableRepository).deleteById(anyLong());
        assertThrows(IllegalArgumentException.class, () -> tableService.deleteTable(table.getTableId()));
        verify(tableRepository, times(1)).deleteById(table.getTableId());
    }
}