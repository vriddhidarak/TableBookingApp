package com.vriddhi.tablebookingapp.dto;

import com.vriddhi.tablebookingapp.model.Reservation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.vriddhi.tablebookingapp.dto.ReservationRestaurantResponseDTO.mapToRestaurantDTO;
import static com.vriddhi.tablebookingapp.dto.TableResponseDTO.mapToTableResponseDTO;
import static com.vriddhi.tablebookingapp.dto.UserResponseDTO.mapToUserResponseDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponseDTO {
    private Long reservationId;
    private LocalDateTime reservationDateTime;
    private int partySize;
    private UserResponseDTO user;
    private TableResponseDTO table;
    private ReservationRestaurantResponseDTO restaurant;

    public static ReservationResponseDTO mapToReservationResponseDTO(Reservation reservation) {
        ReservationResponseDTO responseDTO = new ReservationResponseDTO();
        responseDTO.setReservationId(reservation.getReservationId());
        responseDTO.setReservationDateTime(reservation.getReservationDateTime());
        responseDTO.setPartySize(reservation.getPartySize());
        responseDTO.setUser(mapToUserResponseDTO(reservation.getUser()));
        responseDTO.setTable(mapToTableResponseDTO(reservation.getTable()));
        responseDTO.setRestaurant(mapToRestaurantDTO(reservation.getRestaurant()));
        return responseDTO;
    }
}

