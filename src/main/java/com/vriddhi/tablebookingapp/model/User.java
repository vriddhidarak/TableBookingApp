package com.vriddhi.tablebookingapp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.List;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@jakarta.persistence.Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Size(min = 1, max = 100)
    @NotNull
    private String userName;

    @Email(regexp = "^[A-Za-z0-9._%+-]+@[a-z]\\.com$", message = "email should be a valid mail")
    @NotNull
    @Column(unique = true)
    private String email;

    @Size(min = 8, message = "password must be greater than 8 characters")
    @NotNull
    private String password;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    @NotNull
    private String phone;

    @NotNull
    private String userAddress;


    @OneToMany(mappedBy = "user")
    @JsonManagedReference("user-reservation")
    @ToString.Exclude
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference("user-ratingReview")
    @ToString.Exclude
    private List<RatingReview> ratingReviews;


    public User(long userId, String userName, String email, String password, String phone, String userAddress) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.userAddress = userAddress;
    }

}



