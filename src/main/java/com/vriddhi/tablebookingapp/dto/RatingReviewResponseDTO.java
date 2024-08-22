package com.vriddhi.tablebookingapp.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingReviewResponseDTO {
    private Long ratingId;
    private Long restaurantId;
    private Long userId;
    private int rating;
    private String review;
}
