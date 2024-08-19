package com.vriddhi.tablebookingapp.repository;

import com.vriddhi.tablebookingapp.model.Reservation;
import com.vriddhi.tablebookingapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {


    List<Reservation> findByUser(Optional<User> user);
}
