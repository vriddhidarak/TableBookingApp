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
public class RatingReviewService implements RatingReviewServiceInterface {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RatingReviewRepository ratingReviewRepository;

    @Override
    @Transactional
    public RatingReview saveRatingReview(RatingReviewDTO ratingReview) {
        return ratingReviewRepository.save(mapDTOToEntity(ratingReview));
    }

    @Override
    public Optional<RatingReview> getRatingReviewById(Long ratingId) {
        return ratingReviewRepository.findById(ratingId);
    }

    @Override
    public List<RatingReview> getRatingsReviewsByRestaurantId(Long restaurantId) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        return ratingReviewRepository.findByRestaurant(restaurant); // Modify this to filter by restaurantId
    }

    @Override
    @Transactional
    public void deleteRatingReview(Long ratingId) {
        ratingReviewRepository.deleteById(ratingId);
    }

    private RatingReview mapDTOToEntity(RatingReviewDTO ratingReviewDTO) {
        RatingReview ratingReview1 = new RatingReview();
        ratingReview1.setRating(ratingReviewDTO.getRating());
        ratingReview1.setReview(ratingReviewDTO.getReview());
        ratingReview1.setRestaurant(restaurantRepository.findById(ratingReviewDTO.getRestaurantId()).get());
        ratingReview1.setUser(userRepository.findById(ratingReviewDTO.getUserId()).get());
        return ratingReview1;
    }


}
