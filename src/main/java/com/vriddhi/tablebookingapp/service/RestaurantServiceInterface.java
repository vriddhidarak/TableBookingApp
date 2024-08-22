package com.vriddhi.tablebookingapp.service;

import com.vriddhi.tablebookingapp.dto.RestaurantResponse1DTO;
import com.vriddhi.tablebookingapp.model.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantServiceInterface {
    List<RestaurantResponse1DTO> getAllRestaurants();
    Optional<RestaurantResponse1DTO> getRestaurantById(Long restaurantId);
    RestaurantResponse1DTO saveRestaurant(Restaurant restaurant);
    void deleteRestaurant(Long restaurantId);
}
