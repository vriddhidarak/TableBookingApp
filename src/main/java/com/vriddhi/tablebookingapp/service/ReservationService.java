package com.vriddhi.tablebookingapp.service;


import com.vriddhi.tablebookingapp.dto.ReservationDTO;
import com.vriddhi.tablebookingapp.model.Reservation;
import com.vriddhi.tablebookingapp.model.Restaurant;
import com.vriddhi.tablebookingapp.model.Table;
import com.vriddhi.tablebookingapp.model.User;
import com.vriddhi.tablebookingapp.repository.ReservationRepository;
import com.vriddhi.tablebookingapp.repository.RestaurantRepository;
import com.vriddhi.tablebookingapp.repository.TableRepository;
import com.vriddhi.tablebookingapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public Reservation saveReservation(ReservationDTO reservation) {
        System.out.println("ReservationService.saveReservation DTO  "+ reservation);
        Reservation newReservation = new Reservation();
        User user = userRepository.findById(reservation.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        Table table = tableRepository.findById(reservation.getTableId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid table ID"));
        Restaurant restaurant = restaurantRepository.findById(reservation.getRestaurantId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid restaurant ID"));

        newReservation.setReservationDateTime(reservation.getReservationDateTime());
        newReservation.setPartySize(reservation.getPartySize());
        newReservation.setUser(user);
        newReservation.setTable(table);
        newReservation.setRestaurant(restaurant);

        System.out.println("ReservationService.saveReservation   "+ newReservation);

        return reservationRepository.save(newReservation);
    }
    public Optional<Reservation> getReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId);
    }

   public void deleteReservation(Long reservationId) {

        try {
            reservationRepository.deleteById(reservationId);

        }
        catch (Exception e) {
            System.out.println("Error deleting reservation: " + e.getMessage());
        }
    }
    public List<Reservation> getReservationsByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return reservationRepository.findByUser(user);
    }
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }
}

