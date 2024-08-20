package com.vriddhi.tablebookingapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private int tableNumber;

    @NotNull
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

    public Table(long l, String s, int i, Restaurant restaurant) {
        this.tableId = l;
        this.tableNumber = i;
        this.totalSeats = i;
        this.restaurant = restaurant;
    }
}

