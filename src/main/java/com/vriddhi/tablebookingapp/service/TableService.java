package com.vriddhi.tablebookingapp.service;

import com.vriddhi.tablebookingapp.dto.TableResponseDTO;
import com.vriddhi.tablebookingapp.model.Restaurant;
import com.vriddhi.tablebookingapp.model.Table;
import com.vriddhi.tablebookingapp.repository.RestaurantRepository;
import com.vriddhi.tablebookingapp.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.vriddhi.tablebookingapp.dto.TableResponseDTO.mapToTableResponseDTO;

@Service
public class TableService implements TableServiceInterface {

    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private TableRepository tableRepository;

    @Override
    public List<TableResponseDTO> getTablesByRestaurantId(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
        return tableRepository.findByRestaurant(restaurant).stream().map(TableResponseDTO::mapToTableResponseDTO).toList();
    }

    @Override
    public Optional<TableResponseDTO> getTableById(Long tableId) {

        return tableRepository.findById(tableId).map(TableResponseDTO::mapToTableResponseDTO);
    }

    @Override
    @Transactional
    public TableResponseDTO saveTable(Table table, Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
        table.setRestaurant(restaurant);
        restaurant.setRestaurantTotalTableCount(restaurant.getRestaurantTotalTableCount() + 1);
        return mapToTableResponseDTO(tableRepository.save(table));
    }

    @Override
    @Transactional
    public void deleteTable(Long tableId) {
        //decrease the restaurant total table count
        Table table = tableRepository.findById(tableId)
                .orElseThrow(() -> new RuntimeException("Table not found"));
        Restaurant restaurant = table.getRestaurant();
        restaurant.setRestaurantTotalTableCount(restaurant.getRestaurantTotalTableCount() - 1);
        tableRepository.deleteById(tableId);
    }


}
