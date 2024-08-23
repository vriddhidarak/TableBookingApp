package com.vriddhi.tablebookingapp.dto;

import com.vriddhi.tablebookingapp.exception.ResourceNotFoundException;
import com.vriddhi.tablebookingapp.model.Reservation;
import com.vriddhi.tablebookingapp.model.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableResponseDTO {
    private Long tableId;
    private int tableNumber;
    private int totalSeats;
    private List<TableReservationDTO> tableReservations;

    public static TableResponseDTO mapToTableResponseDTO(Table table) {
        if(table==null){
            throw new ResourceNotFoundException("Table not found");
        }
        TableResponseDTO tableResponseDTO = new TableResponseDTO();
        tableResponseDTO.setTableId(table.getTableId());
        tableResponseDTO.setTableNumber(table.getTableNumber());
        tableResponseDTO.setTotalSeats(table.getTotalSeats());
        List<TableReservationDTO> tableReservationDTOList = new ArrayList<>();
        for(Reservation reservation: table.getReservations()){
            tableReservationDTOList.add(TableReservationDTO.mapToReservationDTO(reservation));
        }
        tableResponseDTO.setTableReservations(tableReservationDTOList);
        return tableResponseDTO;
    }
}


@Data
@NoArgsConstructor
@AllArgsConstructor
class TableReservationDTO{
    private Long reservationId;
    private LocalDateTime reservationDateTime;
    private int partySize;

    public static TableReservationDTO mapToReservationDTO(Reservation reservation) {
        TableReservationDTO responseDTO = new TableReservationDTO();
        responseDTO.setReservationId(reservation.getReservationId());
        responseDTO.setReservationDateTime(reservation.getReservationDateTime());
        responseDTO.setPartySize(reservation.getPartySize());
        return responseDTO;
    }
}
