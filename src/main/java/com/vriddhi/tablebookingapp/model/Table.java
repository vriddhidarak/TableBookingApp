package com.vriddhi.tablebookingapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import lombok.*;

import java.util.List;


@Entity
@jakarta.persistence.Table(name = "tables")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Table {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tableId;

    private int tableNumber;
    private int totalSeats;


    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonBackReference("restaurant-table")
    @ToString.Exclude
    private Restaurant restaurant;

    @OneToMany(mappedBy = "table")
    @JsonManagedReference("table-reservation")
    @ToString.Exclude
    private List<Reservation> reservations;
}

