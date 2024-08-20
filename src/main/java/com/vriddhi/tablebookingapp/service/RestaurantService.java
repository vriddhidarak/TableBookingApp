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
public class RestaurantService implements RestaurantServiceInterface {

    @Autowired
    private RestaurantRepository restaurantRepository;


    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public Optional<Restaurant> getRestaurantById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId);
    }

    @Override
    @Transactional
    public Restaurant saveRestaurant(Restaurant restaurant) {
        restaurant.setRestaurantTotalTableCount(0);
        return restaurantRepository.save(restaurant);
    }

    @Override
    @Transactional
    public void deleteRestaurant(Long restaurantId) {
        restaurantRepository.deleteById(restaurantId);
    }

//    @Transactional
//    private void updateRestaurantTotalTableCount(Restaurant restaurant) {
//        List<Table> tables = restaurant.getTables();
//        restaurant.setRestaurantTotalTableCount(tables != null ? tables.size() : 0);
//        restaurantRepository.save(restaurant);
//    }
}
