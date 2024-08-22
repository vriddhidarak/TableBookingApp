package com.vriddhi.tablebookingapp.dto;

import com.vriddhi.tablebookingapp.exception.ResourceNotFoundException;
import com.vriddhi.tablebookingapp.model.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableResponseDTO {
    private Long tableId;
    private int tableNumber;
    private int totalSeats;

    public static TableResponseDTO mapToTableDTO(Table table) {
        if(table==null){
            throw new ResourceNotFoundException("Table not found");
        }
        TableResponseDTO tableResponseDTO = new TableResponseDTO();
        tableResponseDTO.setTableId(table.getTableId());
        tableResponseDTO.setTableNumber(table.getTableNumber());
        tableResponseDTO.setTotalSeats(table.getTotalSeats());
        return tableResponseDTO;
    }
}
