package com.vriddhi.tablebookingapp.dto;

import com.vriddhi.tablebookingapp.model.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRestaurantResponseDTO {
    private Long restaurantId;
    private String restaurantName;
    private String restaurantLocation;
    private String restaurantDescription;

    public static ReservationRestaurantResponseDTO mapToRestaurantDTO(Restaurant restaurant) {
        ReservationRestaurantResponseDTO reservationRestaurantResponseDTO = new ReservationRestaurantResponseDTO();
        reservationRestaurantResponseDTO.setRestaurantId(restaurant.getRestaurantId());
        reservationRestaurantResponseDTO.setRestaurantName(restaurant.getRestaurantName());
        reservationRestaurantResponseDTO.setRestaurantLocation(restaurant.getRestaurantLocation());
        reservationRestaurantResponseDTO.setRestaurantDescription(restaurant.getRestaurantDescription());
        return reservationRestaurantResponseDTO;
    }

}
