package com.vriddhi.tablebookingapp.repository;

import com.vriddhi.tablebookingapp.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
