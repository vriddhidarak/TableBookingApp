package com.vriddhi.tablebookingapp.service;

import com.vriddhi.tablebookingapp.dto.RatingReviewDTO;
import com.vriddhi.tablebookingapp.dto.RatingReviewResponseDTO;
import com.vriddhi.tablebookingapp.model.RatingReview;

import java.util.List;
import java.util.Optional;

public interface RatingReviewServiceInterface {
    RatingReviewResponseDTO saveRatingReview(RatingReviewDTO ratingReview);
    Optional<RatingReviewResponseDTO> getRatingReviewById(Long ratingId);
    List<RatingReviewResponseDTO> getRatingsReviewsByRestaurantId(Long restaurantId);
    void deleteRatingReview(Long ratingId);

    List<RatingReviewResponseDTO> getAllRatingReviews();
}
