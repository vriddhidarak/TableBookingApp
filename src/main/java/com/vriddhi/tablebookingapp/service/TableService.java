package com.vriddhi.tablebookingapp.service;


import com.vriddhi.tablebookingapp.model.Table;
import com.vriddhi.tablebookingapp.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TableService {

    @Autowired
    private TableRepository tableRepository;

    public List<Table> getTablesByRestaurantId(Long restaurantId) {
        return tableRepository.findAll(); // Modify this to filter by restaurantId
    }

    public Optional<Table> getTableById(Long tableId) {
        return tableRepository.findById(tableId);
    }

    public Table saveTable(Table table) {
        return tableRepository.save(table);
    }

    public void deleteTable(Long tableId) {
        tableRepository.deleteById(tableId);
    }
}
