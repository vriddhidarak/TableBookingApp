package com.vriddhi.tablebookingapp.service;

import com.vriddhi.tablebookingapp.dto.ReservationDTO;
import com.vriddhi.tablebookingapp.dto.TableResponseDTO;
import com.vriddhi.tablebookingapp.model.Reservation;
import com.vriddhi.tablebookingapp.model.Restaurant;
import com.vriddhi.tablebookingapp.model.Table;
import com.vriddhi.tablebookingapp.repository.RestaurantRepository;
import com.vriddhi.tablebookingapp.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        return tableRepository.findByRestaurant(restaurant).stream().map(this::mapToTableResponseDTO).toList();
    }

    @Override
    public Optional<TableResponseDTO> getTableById(Long tableId) {

        return tableRepository.findById(tableId).map(this::mapToTableResponseDTO);
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
        tableRepository.deleteById(tableId);
    }

    private TableResponseDTO mapToTableResponseDTO(Table table) {
        TableResponseDTO tableResponseDTO = new TableResponseDTO();
        tableResponseDTO.setTableId(table.getTableId());
        tableResponseDTO.setTableNumber(table.getTableNumber());
        tableResponseDTO.setTotalSeats(table.getTotalSeats());
        for(Reservation reservation : table.getReservations()) {
            tableResponseDTO.setReservation(
                    new ReservationDTO(reservation.getUser().getUserId(),
                            reservation.getTable().getTableId(),
                            reservation.getRestaurant().getRestaurantId(),
                            reservation.getReservationDateTime(),
                            reservation.getPartySize()));
        }

        return tableResponseDTO;
    }
}
