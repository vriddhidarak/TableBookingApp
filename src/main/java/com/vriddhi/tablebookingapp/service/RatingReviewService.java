package com.vriddhi.tablebookingapp.service;

import com.vriddhi.tablebookingapp.model.RatingReview;
import com.vriddhi.tablebookingapp.repository.RatingReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingReviewService {

    @Autowired
    private RatingReviewRepository ratingReviewRepository;

    public RatingReview saveRatingReview(RatingReview ratingReview) {
        return ratingReviewRepository.save(ratingReview);
    }

    public Optional<RatingReview> getRatingReviewById(Long ratingId) {
        return ratingReviewRepository.findById(ratingId);
    }

    public List<RatingReview> getRatingsReviewsByRestaurantId(Long restaurantId) {
        return ratingReviewRepository.findAll(); // Modify this to filter by restaurantId
    }

    public void deleteRatingReview(Long ratingId) {
        ratingReviewRepository.deleteById(ratingId);
    }
}
