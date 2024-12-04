package com.vriddhi.tablebookingapp.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@jakarta.persistence.Table(name = "restaurants")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantId;

    private String restaurantName;

    private String restaurantLocation;

    private int restaurantTotalTableCount;
    private String restaurantDescription;
    private String restaurantCity;

    @OneToMany(mappedBy = "restaurant")
    private List<Table> tables;

    @OneToMany(mappedBy = "restaurant")
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "restaurant")
    private List<RatingReview> ratingReviews;


    public Restaurant(long l, String testRestaurant) {
        this.restaurantId = l;
        this.restaurantName = testRestaurant;
        this.tables = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.ratingReviews = new ArrayList<>();

    }
}
