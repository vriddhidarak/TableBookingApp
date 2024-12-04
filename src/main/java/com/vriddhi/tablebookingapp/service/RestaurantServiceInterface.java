package com.vriddhi.tablebookingapp.service;

import com.vriddhi.tablebookingapp.dto.RestaurantResponseDTO;
import com.vriddhi.tablebookingapp.model.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantServiceInterface {
    List<RestaurantResponseDTO> getAllRestaurants();
    Optional<RestaurantResponseDTO> getRestaurantById(Long restaurantId);
    RestaurantResponseDTO saveRestaurant(Restaurant restaurant);
    void deleteRestaurant(Long restaurantId);
}
