package com.vriddhi.tablebookingapp.service;

import com.vriddhi.tablebookingapp.dto.RatingReviewDTO;
import com.vriddhi.tablebookingapp.model.RatingReview;

import java.util.List;
import java.util.Optional;

public interface RatingReviewServiceInterface {
    RatingReview saveRatingReview(RatingReviewDTO ratingReview);
    Optional<RatingReview> getRatingReviewById(Long ratingId);
    List<RatingReview> getRatingsReviewsByRestaurantId(Long restaurantId);
    void deleteRatingReview(Long ratingId);
}
