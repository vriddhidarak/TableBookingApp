package com.vriddhi.tablebookingapp.repository;


import com.vriddhi.tablebookingapp.model.RatingReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingReviewRepository extends JpaRepository<RatingReview, Long> {
}
