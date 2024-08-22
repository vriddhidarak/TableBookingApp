package com.vriddhi.tablebookingapp.dto;


import com.vriddhi.tablebookingapp.model.Reservation;
import com.vriddhi.tablebookingapp.model.Restaurant;
import com.vriddhi.tablebookingapp.model.Table;
import com.vriddhi.tablebookingapp.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequestDTO {
    private Long userId;
    private Long tableId;
    private Long restaurantId;
    private LocalDateTime reservationDateTime;
    private int partySize;

    public static Reservation mapToReservationEntity(ReservationRequestDTO reservationRequestDTO, User user, Table table, Restaurant restaurant) {
        Reservation reservation = new Reservation();
        reservation.setReservationDateTime(reservationRequestDTO.getReservationDateTime());
        reservation.setPartySize(reservationRequestDTO.getPartySize());
        reservation.setUser(user);
        reservation.setTable(table);
        reservation.setRestaurant(restaurant);
        return reservation;
    }
}
