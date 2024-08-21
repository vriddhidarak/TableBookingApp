package com.vriddhi.tablebookingapp.dto;

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
public class ReservationResponseDTO {
    private Long reservationId;
    private LocalDateTime reservationDateTime;
    private int partySize;
    private UserResponseDTO user;
    private TableResponseDTO table;
    private RestaurantResponseDTO restaurant;
}