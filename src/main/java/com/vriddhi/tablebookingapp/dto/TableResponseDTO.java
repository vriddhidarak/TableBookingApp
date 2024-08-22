package com.vriddhi.tablebookingapp.dto;

import com.vriddhi.tablebookingapp.model.Reservation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableResponseDTO {
    private Long tableId;
    private int tableNumber;
    private int totalSeats;
    private ReservationDTO reservation;

}
