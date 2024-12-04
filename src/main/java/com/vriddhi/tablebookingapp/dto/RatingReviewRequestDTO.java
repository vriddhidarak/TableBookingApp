package com.vriddhi.tablebookingapp.dto;

import com.vriddhi.tablebookingapp.model.RatingReview;
import com.vriddhi.tablebookingapp.repository.RestaurantRepository;
import com.vriddhi.tablebookingapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingReviewRequestDTO {
    private Long restaurantId;
    private Long userId;
    private int rating;
    private String review;

    public static RatingReview mapDTOToEntity(RatingReviewRequestDTO ratingReviewRequestDTO, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        RatingReview ratingReview1 = new RatingReview();
        ratingReview1.setRating(ratingReviewRequestDTO.getRating());
        ratingReview1.setReview(ratingReviewRequestDTO.getReview());
        ratingReview1.setRestaurant(restaurantRepository.findById(ratingReviewRequestDTO.getRestaurantId()).get());
        ratingReview1.setUser(userRepository.findById(ratingReviewRequestDTO.getUserId()).get());
        return ratingReview1;
    }

    public static RatingReviewRequestDTO maptoRatingReviewDTO(RatingReview ratingReview){
        RatingReviewRequestDTO ratingReviewRequestDTO = new RatingReviewRequestDTO();
        ratingReviewRequestDTO.setRestaurantId(ratingReview.getRestaurant().getRestaurantId());
        ratingReviewRequestDTO.setRating(ratingReview.getRating());
        ratingReviewRequestDTO.setReview(ratingReview.getReview());
        return ratingReviewRequestDTO;
    }
}
