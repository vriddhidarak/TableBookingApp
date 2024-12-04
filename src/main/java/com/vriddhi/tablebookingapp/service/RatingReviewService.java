package com.vriddhi.tablebookingapp.service;

import com.vriddhi.tablebookingapp.dto.RatingReviewRequestDTO;
import com.vriddhi.tablebookingapp.dto.RatingReviewResponseDTO;
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

import static com.vriddhi.tablebookingapp.dto.RatingReviewRequestDTO.mapDTOToEntity;
import static com.vriddhi.tablebookingapp.dto.RatingReviewResponseDTO.mapToResponseDTO;

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
    public RatingReviewResponseDTO saveRatingReview(RatingReviewRequestDTO ratingReview) {
        return mapToResponseDTO(ratingReviewRepository.save(mapDTOToEntity(ratingReview,restaurantRepository,userRepository)));
    }

    @Override
    public Optional<RatingReviewResponseDTO> getRatingReviewById(Long ratingId) {
        return ratingReviewRepository.findById(ratingId).map(RatingReviewResponseDTO::mapToResponseDTO);
    }

    @Override
    public List<RatingReviewResponseDTO> getRatingsReviewsByRestaurantId(Long restaurantId) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);

        return ratingReviewRepository.findByRestaurant(restaurant)
                .stream().map(RatingReviewResponseDTO::mapToResponseDTO).toList(); // Modify this to filter by restaurantId
    }

    @Override
    @Transactional
    public void deleteRatingReview(Long ratingId) {
        ratingReviewRepository.deleteById(ratingId);
    }

    @Override
    public List<RatingReviewResponseDTO> getAllRatingReviews() {

        return ratingReviewRepository.findAll().stream().map(RatingReviewResponseDTO::mapToResponseDTO).toList();
    }

}
