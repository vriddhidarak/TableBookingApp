package com.vriddhi.tablebookingapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "ratings_reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratingId;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
//    @JsonBackReference("restaurant-ratingReview")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
//    @JsonBackReference("user-ratingReview")
    private User user;

    private int rating;
    private String review;
}
