package com.vriddhi.tablebookingapp.service;

import com.vriddhi.tablebookingapp.dto.TableResponseDTO;
import com.vriddhi.tablebookingapp.model.Table;

import java.util.List;
import java.util.Optional;

public interface TableServiceInterface {
    List<TableResponseDTO> getTablesByRestaurantId(Long restaurantId);
    Optional<TableResponseDTO> getTableById(Long tableId);
    TableResponseDTO saveTable(Table table, Long restaurantId);
    void deleteTable(Long tableId);
}
