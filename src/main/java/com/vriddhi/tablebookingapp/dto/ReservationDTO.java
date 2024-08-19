package com.vriddhi.tablebookingapp.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    private Long userId;
    private Long tableId;
    private Long restaurantId;
    private LocalDateTime reservationDateTime;
    private int partySize;
}
