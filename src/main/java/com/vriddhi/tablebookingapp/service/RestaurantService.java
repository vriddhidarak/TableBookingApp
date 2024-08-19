package com.vriddhi.tablebookingapp.service;


import com.vriddhi.tablebookingapp.model.Restaurant;
import com.vriddhi.tablebookingapp.model.Table;
import com.vriddhi.tablebookingapp.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;


    public List<Restaurant> getAllRestaurants() {
        //update total table count for each restaurant
        List<Restaurant> restaurants = restaurantRepository.findAll();
        for (Restaurant restaurant : restaurants) {
            updateRestaurantTotalTableCount(restaurant);
        }
        return restaurantRepository.findAll();
        
    }

    public Optional<Restaurant> getRestaurantById(Long restaurantId) {
        updateRestaurantTotalTableCount(restaurantRepository.findById(restaurantId).get());
        return restaurantRepository.findById(restaurantId);
    }

    public Restaurant saveRestaurant(Restaurant restaurant) {
        updateRestaurantTotalTableCount(restaurant);
        return restaurantRepository.save(restaurant);
    }

    public void deleteRestaurant(Long restaurantId) {
        restaurantRepository.deleteById(restaurantId);
    }
    private void updateRestaurantTotalTableCount(Restaurant restaurant) {
        List<Table> tables = restaurant.getTables();
        restaurant.setRestaurantTotalTableCount(tables != null ? tables.size() : 0);
        restaurantRepository.save(restaurant);

    }
}
