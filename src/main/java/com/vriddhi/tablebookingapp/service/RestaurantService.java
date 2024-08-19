package com.vriddhi.tablebookingapp.service;


import com.vriddhi.tablebookingapp.model.Restaurant;
import com.vriddhi.tablebookingapp.model.Table;
import com.vriddhi.tablebookingapp.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;


    public List<Restaurant> getAllRestaurants() {
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

    @Transactional
    public Restaurant saveRestaurant(Restaurant restaurant) {
        updateRestaurantTotalTableCount(restaurant);
        return restaurantRepository.save(restaurant);
    }

    @Transactional
    public void deleteRestaurant(Long restaurantId) {
        restaurantRepository.deleteById(restaurantId);
    }

    @Transactional
    private void updateRestaurantTotalTableCount(Restaurant restaurant) {
        List<Table> tables = restaurant.getTables();
        restaurant.setRestaurantTotalTableCount(tables != null ? tables.size() : 0);
        restaurantRepository.save(restaurant);

    }
}
