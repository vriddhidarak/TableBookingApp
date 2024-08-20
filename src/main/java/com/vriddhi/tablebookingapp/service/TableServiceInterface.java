package com.vriddhi.tablebookingapp.service;

import com.vriddhi.tablebookingapp.model.Table;

import java.util.List;
import java.util.Optional;

public interface TableServiceInterface {
    List<Table> getTablesByRestaurantId(Long restaurantId);
    Optional<Table> getTableById(Long tableId);
    Table saveTable(Table table, Long restaurantId);
    void deleteTable(Long tableId);
}
