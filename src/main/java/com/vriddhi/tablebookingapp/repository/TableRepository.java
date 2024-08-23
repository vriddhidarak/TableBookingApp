package com.vriddhi.tablebookingapp.repository;

import com.vriddhi.tablebookingapp.model.Restaurant;
import com.vriddhi.tablebookingapp.model.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TableRepository extends JpaRepository<Table, Long> {
    List<Table> findByRestaurant(Restaurant restaurant);
}
