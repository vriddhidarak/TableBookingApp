package com.vriddhi.tablebookingapp.service;

import com.vriddhi.tablebookingapp.dto.*;
import com.vriddhi.tablebookingapp.exception.InvalidInputException;
import com.vriddhi.tablebookingapp.exception.ReservationConflictException;
import com.vriddhi.tablebookingapp.exception.ResourceNotFoundException;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.vriddhi.tablebookingapp.dto.ReservationRequestDTO.mapToReservationEntity;
import static com.vriddhi.tablebookingapp.dto.ReservationResponseDTO.mapToReservationResponseDTO;

@Service
public class ReservationService implements ReservationServiceInterface {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TableRepository tableRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;


    @Transactional
    public ReservationResponseDTO saveReservation(ReservationRequestDTO reservationRequestDTO) {
        Table table = tableRepository.findById(reservationRequestDTO.getTableId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid table ID"));

        validateReservation(reservationRequestDTO, table);

        User user = userRepository.findById(reservationRequestDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        Restaurant restaurant = restaurantRepository.findById(reservationRequestDTO.getRestaurantId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid restaurant ID"));

        Reservation newReservation = mapToReservationEntity(reservationRequestDTO, user, table, restaurant);
        Reservation savedReservation = reservationRepository.save(newReservation);

        return mapToReservationResponseDTO(savedReservation);
    }

    public Optional<ReservationResponseDTO> getReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId).map(ReservationResponseDTO::mapToReservationResponseDTO);
    }

    @Transactional
    public void deleteReservation(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    public List<ReservationResponseDTO> getReservationsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        return reservationRepository.findByUser(Optional.of(user))
                .stream()
                .map(ReservationResponseDTO::mapToReservationResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ReservationResponseDTO> getAllReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(ReservationResponseDTO::mapToReservationResponseDTO)
                .collect(Collectors.toList());
    }

    private void validateReservation(ReservationRequestDTO reservationRequestDTO, Table table) {
        LocalDateTime reservationTime = reservationRequestDTO.getReservationDateTime();
        Optional<Reservation> existingReservation = reservationRepository
                .findByTableAndReservationDateTime(table, reservationTime);

        if (existingReservation.isPresent()) {
            throw new ReservationConflictException("Reservation conflict: Table is already reserved at this time.");
        }

        if (reservationRequestDTO.getPartySize() > table.getTotalSeats()) {
            throw new InvalidInputException("Party size exceeds table capacity.");
        }
    }

}