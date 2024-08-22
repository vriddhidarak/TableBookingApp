package com.vriddhi.tablebookingapp.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantResponse1DTO {
    private Long restaurantId;
    private String restaurantName;
    private String restaurantLocation;
    private String restaurantDescription;
    private int restaurantTotalTableCount;
    private List<TableResponseDTO> tables;
    private List<ReservationDTO> reservations;
    private List<RatingReviewDTO> ratingReviews;
}
