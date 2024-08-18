package com.vriddhi.tablebookingapp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@jakarta.persistence.Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String userName;

    @Column(unique = true)
    private String email;

    private String password;

    private String phone;

    private String userAddress;


    @OneToMany(mappedBy = "user")
    @JsonManagedReference("user-reservation")
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference("user-ratingReview")
    private List<RatingReview> ratingReviews;
}



