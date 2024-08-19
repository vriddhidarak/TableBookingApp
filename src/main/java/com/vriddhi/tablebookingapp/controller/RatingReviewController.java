package com.vriddhi.tablebookingapp.controller;

import com.vriddhi.tablebookingapp.dto.RatingReviewDTO;
import com.vriddhi.tablebookingapp.model.RatingReview;
import com.vriddhi.tablebookingapp.model.Restaurant;
import com.vriddhi.tablebookingapp.service.RatingReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ratings-reviews")
public class RatingReviewController {

    private static final Logger log = LoggerFactory.getLogger(RatingReviewController.class);
    @Autowired
    private RatingReviewService ratingReviewService;

    @PostMapping
    public ResponseEntity<RatingReview> createRatingReview(@RequestBody RatingReviewDTO ratingReview) {
        RatingReview newRatingReview = ratingReviewService.saveRatingReview(ratingReview);
        return ResponseEntity.ok(newRatingReview);
    }

    @GetMapping("/{ratingId}")
    public ResponseEntity<RatingReview> getRatingReview(@PathVariable Long ratingId) {
        Optional<RatingReview> ratingReview = ratingReviewService.getRatingReviewById(ratingId);
        return ratingReview.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/restaurant/{restaurantId}")
    public List<RatingReview> getRatingsReviewsByRestaurant(@PathVariable Long restaurantId) {
        return ratingReviewService.getRatingsReviewsByRestaurantId(restaurantId);
    }

    @DeleteMapping("/{ratingId}")
    public ResponseEntity<Void> deleteRatingReview(@PathVariable Long ratingId) {
        ratingReviewService.deleteRatingReview(ratingId);
        return ResponseEntity.noContent().build();
    }
}
