package com.vriddhi.tablebookingapp.repository;


import com.vriddhi.tablebookingapp.model.RatingReview;
import com.vriddhi.tablebookingapp.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingReviewRepository extends JpaRepository<RatingReview, Long> {
    List<RatingReview> findByRestaurant(Optional<Restaurant> restaurant);
}
