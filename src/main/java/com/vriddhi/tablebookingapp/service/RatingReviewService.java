package com.vriddhi.tablebookingapp.service;

import com.vriddhi.tablebookingapp.dto.RatingReviewDTO;
import com.vriddhi.tablebookingapp.model.RatingReview;
import com.vriddhi.tablebookingapp.model.Restaurant;
import com.vriddhi.tablebookingapp.repository.RatingReviewRepository;
import com.vriddhi.tablebookingapp.repository.RestaurantRepository;
import com.vriddhi.tablebookingapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RatingReviewService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RatingReviewRepository ratingReviewRepository;

    @Transactional
    public RatingReview saveRatingReview(RatingReviewDTO ratingReview) {
        RatingReview ratingReview1 = new RatingReview();
        ratingReview1.setRating(ratingReview.getRating());
        ratingReview1.setReview(ratingReview.getReview());
        ratingReview1.setRestaurant(restaurantRepository.findById(ratingReview.getRestaurantId()).get());
        ratingReview1.setUser(userRepository.findById(ratingReview.getUserId()).get());
        return ratingReviewRepository.save(ratingReview1);
    }

    public Optional<RatingReview> getRatingReviewById(Long ratingId) {
        return ratingReviewRepository.findById(ratingId);
    }

    public List<RatingReview> getRatingsReviewsByRestaurantId(Long restaurantId) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        return ratingReviewRepository.findByRestaurant(restaurant); // Modify this to filter by restaurantId
    }

    @Transactional
    public void deleteRatingReview(Long ratingId) {
        ratingReviewRepository.deleteById(ratingId);
    }
}
