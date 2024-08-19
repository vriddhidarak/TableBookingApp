package com.vriddhi.tablebookingapp.model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

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
    @JsonManagedReference("restaurant-table")
    @ToString.Exclude
    private List<Table> tables;

    @OneToMany(mappedBy = "restaurant")
    @JsonManagedReference("restaurant-reservation")
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "restaurant")
    @JsonManagedReference("restaurant-ratingReview")
    private List<RatingReview> ratingReviews;


}
