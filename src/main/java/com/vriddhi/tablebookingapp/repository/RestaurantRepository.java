package com.vriddhi.tablebookingapp.repository;

import com.vriddhi.tablebookingapp.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
