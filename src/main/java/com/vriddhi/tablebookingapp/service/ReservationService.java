package com.vriddhi.tablebookingapp.service;


import com.vriddhi.tablebookingapp.model.Reservation;
import com.vriddhi.tablebookingapp.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public Optional<Reservation> getReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId);
    }

    public List<Reservation> getReservationsByUserId(Long userId) {
        return reservationRepository.findAll(); // Modify this to filter by userId
    }

    public void deleteReservation(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }
}

