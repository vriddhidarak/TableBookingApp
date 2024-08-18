package com.vriddhi.tablebookingapp.service;


import com.vriddhi.tablebookingapp.model.Restaurant;
import com.vriddhi.tablebookingapp.model.Table;
import com.vriddhi.tablebookingapp.repository.RestaurantRepository;
import com.vriddhi.tablebookingapp.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TableService {

    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private TableRepository tableRepository;

    public List<Table> getTablesByRestaurantId(Long restaurantId) {
        return tableRepository.findAll(); // Modify this to filter by restaurantId
    }

    public Optional<Table> getTableById(Long tableId) {
        return tableRepository.findById(tableId);
    }

    public Table saveTable(Table table, Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
        table.setRestaurant(restaurant);
        return tableRepository.save(table);
    }

    public void deleteTable(Long tableId) {
        tableRepository.deleteById(tableId);
    }
}
