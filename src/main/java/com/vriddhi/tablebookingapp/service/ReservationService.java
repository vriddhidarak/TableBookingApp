package com.vriddhi.tablebookingapp.service;

import com.vriddhi.tablebookingapp.dto.ReservationDTO;
import com.vriddhi.tablebookingapp.dto.ReservationResponseDTO;
import com.vriddhi.tablebookingapp.exception.InvalidInputException;
import com.vriddhi.tablebookingapp.exception.ReservationConflictException;
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
    public ReservationResponseDTO saveReservation(ReservationDTO reservationDTO) {
        Table table = tableRepository.findById(reservationDTO.getTableId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid table ID"));

        validateReservation(reservationDTO, table);

        User user = userRepository.findById(reservationDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        Restaurant restaurant = restaurantRepository.findById(reservationDTO.getRestaurantId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid restaurant ID"));

        Reservation newReservation = mapToReservationEntity(reservationDTO, user, table, restaurant);
        Reservation savedReservation = reservationRepository.save(newReservation);

        return mapToReservationResponseDTO(savedReservation);
    }

    public Optional<ReservationResponseDTO> getReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId).map(this::mapToReservationResponseDTO);
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
                .map(this::mapToReservationResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ReservationResponseDTO> getAllReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(this::mapToReservationResponseDTO)
                .collect(Collectors.toList());
    }

    private void validateReservation(ReservationDTO reservationDTO, Table table) {
        LocalDateTime reservationTime = reservationDTO.getReservationDateTime();
        Optional<Reservation> existingReservation = reservationRepository
                .findByTableAndReservationDateTime(table, reservationTime);

        if (existingReservation.isPresent()) {
            throw new ReservationConflictException("Reservation conflict: Table is already reserved at this time.");
        }

        if (reservationDTO.getPartySize() > table.getTotalSeats()) {
            throw new InvalidInputException("Party size exceeds table capacity.");
        }
    }

    private Reservation mapToReservationEntity(ReservationDTO reservationDTO, User user, Table table, Restaurant restaurant) {
        Reservation reservation = new Reservation();
        reservation.setReservationDateTime(reservationDTO.getReservationDateTime());
        reservation.setPartySize(reservationDTO.getPartySize());
        reservation.setUser(user);
        reservation.setTable(table);
        reservation.setRestaurant(restaurant);
        return reservation;
    }

    private ReservationResponseDTO mapToReservationResponseDTO(Reservation reservation) {
        ReservationResponseDTO responseDTO = new ReservationResponseDTO();
        responseDTO.setReservationId(reservation.getReservationId());
        responseDTO.setReservationDateTime(reservation.getReservationDateTime());
        responseDTO.setPartySize(reservation.getPartySize());
        responseDTO.setUser(reservation.getUser());
        responseDTO.setTable(reservation.getTable());
        responseDTO.setRestaurant(reservation.getRestaurant());
        return responseDTO;
    }
}