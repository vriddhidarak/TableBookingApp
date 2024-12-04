package com.vriddhi.tablebookingapp.service;

import com.vriddhi.tablebookingapp.dto.RatingReviewRequestDTO;
import com.vriddhi.tablebookingapp.dto.ReservationRequestDTO;
import com.vriddhi.tablebookingapp.dto.RestaurantResponseDTO;
import com.vriddhi.tablebookingapp.dto.TableResponseDTO;
import com.vriddhi.tablebookingapp.model.RatingReview;
import com.vriddhi.tablebookingapp.model.Reservation;
import com.vriddhi.tablebookingapp.model.Restaurant;
import com.vriddhi.tablebookingapp.model.Table;
import com.vriddhi.tablebookingapp.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.vriddhi.tablebookingapp.dto.RestaurantResponseDTO.mapToRestaurantResponseDTO;

@Service
public class RestaurantService implements RestaurantServiceInterface {

    @Autowired
    private RestaurantRepository restaurantRepository;


    @Override
    public List<RestaurantResponseDTO> getAllRestaurants() {
        return restaurantRepository.findAll().stream().map(RestaurantResponseDTO::mapToRestaurantResponseDTO).toList();
    }

    @Override
    public Optional<RestaurantResponseDTO> getRestaurantById(Long restaurantId) {

        return restaurantRepository.findById(restaurantId).map(RestaurantResponseDTO::mapToRestaurantResponseDTO);
    }

    @Override
    @Transactional
    public RestaurantResponseDTO saveRestaurant(Restaurant restaurant) {
        if(restaurant==null){
            throw new IllegalArgumentException("Restaurant cannot be null");
        }
        restaurant.setRestaurantTotalTableCount(0);
        restaurant.setTables(new ArrayList<>());
        restaurant.setReservations(new ArrayList<>());
        restaurant.setRatingReviews(new ArrayList<>());
        return mapToRestaurantResponseDTO(restaurantRepository.save(restaurant));
    }

    @Override
    @Transactional
    public void deleteRestaurant(Long restaurantId) {
        restaurantRepository.deleteById(restaurantId);
    }


}
