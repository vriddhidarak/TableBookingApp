package com.vriddhi.tablebookingapp.service;

import com.vriddhi.tablebookingapp.model.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantServiceInterface {
    List<Restaurant> getAllRestaurants();
    Optional<Restaurant> getRestaurantById(Long restaurantId);
    Restaurant saveRestaurant(Restaurant restaurant);
    void deleteRestaurant(Long restaurantId);
}
