package com.vriddhi.tablebookingapp.dto;


import com.vriddhi.tablebookingapp.model.RatingReview;
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

    public static RatingReviewResponseDTO mapToResponseDTO(RatingReview ratingReview) {
        RatingReviewResponseDTO ratingReviewResponseDTO = new RatingReviewResponseDTO();
        ratingReviewResponseDTO.setRatingId(ratingReview.getRatingId());
        ratingReviewResponseDTO.setRating(ratingReview.getRating());
        ratingReviewResponseDTO.setReview(ratingReview.getReview());
        ratingReviewResponseDTO.setRestaurantId(ratingReview.getRestaurant().getRestaurantId());
        ratingReviewResponseDTO.setUserId(ratingReview.getUser().getUserId());
        return ratingReviewResponseDTO;
    }
}
