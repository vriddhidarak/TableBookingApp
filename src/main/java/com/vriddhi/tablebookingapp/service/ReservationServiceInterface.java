package com.vriddhi.tablebookingapp.service;

import com.vriddhi.tablebookingapp.dto.ReservationDTO;
import com.vriddhi.tablebookingapp.dto.ReservationResponseDTO;

import java.util.List;
import java.util.Optional;

public interface ReservationServiceInterface {
    ReservationResponseDTO saveReservation(ReservationDTO reservationDTO);
    Optional<ReservationResponseDTO> getReservationById(Long reservationId);
    void deleteReservation(Long reservationId);
    List<ReservationResponseDTO> getReservationsByUserId(Long userId);
    List<ReservationResponseDTO> getAllReservations();
}
