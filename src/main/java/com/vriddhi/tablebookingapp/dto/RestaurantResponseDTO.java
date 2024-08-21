package com.vriddhi.tablebookingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantResponseDTO {
    private Long restaurantId;
    private String restaurantName;
    private String restaurantLocation;
    private String restaurantDescription;

}
