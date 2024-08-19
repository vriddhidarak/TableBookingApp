package com.vriddhi.tablebookingapp.controller;

import com.vriddhi.tablebookingapp.dto.ReservationDTO;
import com.vriddhi.tablebookingapp.model.Reservation;
import com.vriddhi.tablebookingapp.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@Slf4j
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }
    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationDTO reservation) {
        Reservation newReservation = reservationService.saveReservation(reservation);
        return ResponseEntity.ok(newReservation);
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<Reservation> getReservation(@PathVariable Long reservationId) {
        Optional<Reservation> reservation = reservationService.getReservationById(reservationId);

        return reservation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public List<Reservation> getReservationsByUser(@PathVariable Long userId) {
        return reservationService.getReservationsByUserId(userId);
    }

    @RequestMapping(path="/{reservationId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteReservation(@PathVariable Long reservationId) {
        log.info("ReservationController.deleteReservation  {}", reservationId);
        reservationService.deleteReservation(reservationId);
        return new ResponseEntity<>("Reservation deleted successfully", null, 200);
    }
}
