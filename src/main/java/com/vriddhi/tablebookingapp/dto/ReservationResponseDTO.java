package com.vriddhi.tablebookingapp.dto;

import com.vriddhi.tablebookingapp.exception.ResourceNotFoundException;
import com.vriddhi.tablebookingapp.model.Reservation;
import com.vriddhi.tablebookingapp.model.Restaurant;
import com.vriddhi.tablebookingapp.model.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.vriddhi.tablebookingapp.dto.ReservedRestaurantDTO.mapToRestaurantDTO;
import static com.vriddhi.tablebookingapp.dto.UserResponseDTO.mapToUserResponseDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponseDTO {
    private Long reservationId;
    private LocalDateTime reservationDateTime;
    private int partySize;
    private UserResponseDTO user;
    private ReservedTableDTO table;
    private ReservedRestaurantDTO restaurant;

    public static ReservationResponseDTO mapToReservationResponseDTO(Reservation reservation) {
        ReservationResponseDTO responseDTO = new ReservationResponseDTO();
        responseDTO.setReservationId(reservation.getReservationId());
        responseDTO.setReservationDateTime(reservation.getReservationDateTime());
        responseDTO.setPartySize(reservation.getPartySize());
        responseDTO.setUser(mapToUserResponseDTO(reservation.getUser()));
        responseDTO.setTable(ReservedTableDTO.mapToReservedTableDTO(reservation.getTable()));
        responseDTO.setRestaurant(mapToRestaurantDTO(reservation.getRestaurant()));
        return responseDTO;
    }
}
@Data
@NoArgsConstructor
@AllArgsConstructor
class ReservedTableDTO {
    private Long tableId;
    private int tableNumber;
    private int totalSeats;

    public static ReservedTableDTO mapToReservedTableDTO(Table table) {
        if(table==null){
            throw new ResourceNotFoundException("Table not found");
        }
        ReservedTableDTO tableResponseDTO = new ReservedTableDTO();
        tableResponseDTO.setTableId(table.getTableId());
        tableResponseDTO.setTableNumber(table.getTableNumber());
        tableResponseDTO.setTotalSeats(table.getTotalSeats());
        return tableResponseDTO;
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class ReservedRestaurantDTO {
    private Long restaurantId;
    private String restaurantName;
    private String restaurantLocation;
    private String restaurantDescription;

    public static ReservedRestaurantDTO mapToRestaurantDTO(Restaurant restaurant) {
        ReservedRestaurantDTO reservedRestaurantDTO = new ReservedRestaurantDTO();
        reservedRestaurantDTO.setRestaurantId(restaurant.getRestaurantId());
        reservedRestaurantDTO.setRestaurantName(restaurant.getRestaurantName());
        reservedRestaurantDTO.setRestaurantLocation(restaurant.getRestaurantLocation());
        reservedRestaurantDTO.setRestaurantDescription(restaurant.getRestaurantDescription());
        return reservedRestaurantDTO;
    }

}

