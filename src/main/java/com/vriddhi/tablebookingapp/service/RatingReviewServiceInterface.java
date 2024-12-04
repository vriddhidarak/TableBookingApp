package com.vriddhi.tablebookingapp.service;

import com.vriddhi.tablebookingapp.dto.RatingReviewRequestDTO;
import com.vriddhi.tablebookingapp.dto.RatingReviewResponseDTO;

import java.util.List;
import java.util.Optional;

public interface RatingReviewServiceInterface {
    RatingReviewResponseDTO saveRatingReview(RatingReviewRequestDTO ratingReview);
    Optional<RatingReviewResponseDTO> getRatingReviewById(Long ratingId);
    List<RatingReviewResponseDTO> getRatingsReviewsByRestaurantId(Long restaurantId);
    void deleteRatingReview(Long ratingId);

    List<RatingReviewResponseDTO> getAllRatingReviews();
}
