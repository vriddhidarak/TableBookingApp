package com.vriddhi.tablebookingapp.dto;


import com.vriddhi.tablebookingapp.model.RatingReview;
import com.vriddhi.tablebookingapp.model.Reservation;
import com.vriddhi.tablebookingapp.model.Restaurant;
import com.vriddhi.tablebookingapp.model.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.vriddhi.tablebookingapp.dto.RatingReviewRequestDTO.maptoRatingReviewDTO;
import static com.vriddhi.tablebookingapp.dto.ReservationRequestDTO.maptoReservationDTO;
import static com.vriddhi.tablebookingapp.dto.TableResponseDTO.mapToTableResponseDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantResponseDTO {
    private Long restaurantId;
    private String restaurantName;
    private String restaurantLocation;
    private String restaurantDescription;
    private int restaurantTotalTableCount;
    private List<TableResponseDTO> tables;
    private List<ReservationRequestDTO> reservations;
    private List<RatingReviewRequestDTO> ratingReviews;


    public static RestaurantResponseDTO mapToRestaurantResponseDTO(Restaurant restaurant) {
        RestaurantResponseDTO restaurantResponseDTO = new RestaurantResponseDTO();
        restaurantResponseDTO.setRestaurantId(restaurant.getRestaurantId());
        restaurantResponseDTO.setRestaurantName(restaurant.getRestaurantName());
        restaurantResponseDTO.setRestaurantLocation(restaurant.getRestaurantLocation());
        restaurantResponseDTO.setRestaurantDescription(restaurant.getRestaurantDescription());
        restaurantResponseDTO.setRestaurantTotalTableCount(restaurant.getRestaurantTotalTableCount());
        List<TableResponseDTO> tables = new ArrayList<>();
        for (Table table : restaurant.getTables()) {
            tables.add(mapToTableResponseDTO(table));
        }
        restaurantResponseDTO.setTables(tables);
        List<ReservationRequestDTO> reservations = new ArrayList<>();
        for(Reservation reservation: restaurant.getReservations()){
            reservations.add(maptoReservationDTO(reservation));
        }
        restaurantResponseDTO.setReservations(reservations);
        List<RatingReviewRequestDTO> ratingReviewRequestDTOS = new ArrayList<>();
        for(RatingReview ratingReview: restaurant.getRatingReviews()){
            ratingReviewRequestDTOS.add(maptoRatingReviewDTO(ratingReview));
        }
        restaurantResponseDTO.setRatingReviews(ratingReviewRequestDTOS);
        return restaurantResponseDTO;
    }


}
