package com.vriddhi.tablebookingapp.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

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
    private Set<Table> tables;

    @OneToMany(mappedBy = "restaurant")
    private Set<Reservation> reservations;

    @OneToMany(mappedBy = "restaurant")
    private Set<RatingReview> ratingsReviews;
}
