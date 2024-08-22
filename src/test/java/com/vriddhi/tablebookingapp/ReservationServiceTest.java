package com.vriddhi.tablebookingapp;

import com.vriddhi.tablebookingapp.dto.*;
import com.vriddhi.tablebookingapp.exception.InvalidInputException;
import com.vriddhi.tablebookingapp.exception.ReservationConflictException;
import com.vriddhi.tablebookingapp.model.*;
import com.vriddhi.tablebookingapp.repository.*;
import com.vriddhi.tablebookingapp.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TableRepository tableRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    User user;
    @Mock
    Table table;
    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1L);
        table = new Table();
        table.setTableId(1L);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveReservation_Success() {
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO();
        reservationRequestDTO.setTableId(1L);
        reservationRequestDTO.setUserId(1L);
        reservationRequestDTO.setRestaurantId(1L);
        reservationRequestDTO.setReservationDateTime(LocalDateTime.now().plusDays(1));
        reservationRequestDTO.setPartySize(4);

        Table table = new Table();
        table.setTableId(1L);
        table.setTotalSeats(4);

        User user = new User();
        user.setUserId(1L);

        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(1L);

        when(tableRepository.findById(1L)).thenReturn(Optional.of(table));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(new Reservation());

        ReservationResponseDTO responseDTO = reservationService.saveReservation(reservationRequestDTO);

        assertNotNull(responseDTO);
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    void testSaveReservation_InvalidTableId() {
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO();
        reservationRequestDTO.setTableId(1L);

        when(tableRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> reservationService.saveReservation(reservationRequestDTO));
    }

    @Test
    void testSaveReservation_InvalidUserId() {
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO();
        reservationRequestDTO.setTableId(1L);
        reservationRequestDTO.setUserId(1L);

        when(tableRepository.findById(1L)).thenReturn(Optional.of(table));
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> reservationService.saveReservation(reservationRequestDTO));
    }

    @Test
    void testSaveReservation_InvalidRestaurantId() {
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO();
        reservationRequestDTO.setTableId(1L);
        reservationRequestDTO.setUserId(1L);
        reservationRequestDTO.setRestaurantId(1L);

        when(tableRepository.findById(1L)).thenReturn(Optional.of(table));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(restaurantRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> reservationService.saveReservation(reservationRequestDTO));
    }

    @Test
    void testSaveReservation_ReservationConflict() {
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO();
        reservationRequestDTO.setTableId(1L);
        reservationRequestDTO.setReservationDateTime(LocalDateTime.now().plusDays(1));

        Table table = new Table();
        table.setTableId(1L);

        when(tableRepository.findById(1L)).thenReturn(Optional.of(table));
        when(reservationRepository.findByTableAndReservationDateTime(any(Table.class), any(LocalDateTime.class)))
                .thenReturn(Optional.of(new Reservation()));

        assertThrows(ReservationConflictException.class, () -> reservationService.saveReservation(reservationRequestDTO));
    }

    @Test
    void testSaveReservation_PartySizeExceedsCapacity() {
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO();
        reservationRequestDTO.setTableId(1L);
        reservationRequestDTO.setPartySize(5);

        table.setTotalSeats(4);

        when(tableRepository.findById(1L)).thenReturn(Optional.of(table));

        assertThrows(InvalidInputException.class, () -> reservationService.saveReservation(reservationRequestDTO));
    }

    @Test
    void testGetReservationById_Success() {
        Reservation reservation = new Reservation();
        reservation.setReservationId(1L);

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        Optional<ReservationResponseDTO> responseDTO = reservationService.getReservationById(1L);

        assertTrue(responseDTO.isPresent());
        assertEquals(1L, responseDTO.get().getReservationId());
    }

    @Test
    void testGetReservationById_NotFound() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<ReservationResponseDTO> responseDTO = reservationService.getReservationById(1L);

        assertFalse(responseDTO.isPresent());
    }

    @Test
    void testDeleteReservation_Success() {
        doNothing().when(reservationRepository).deleteById(1L);

        reservationService.deleteReservation(1L);

        verify(reservationRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteReservation_NotFound() {
        doThrow(new IllegalArgumentException("Invalid reservation ID")).when(reservationRepository).deleteById(1L);

        assertThrows(IllegalArgumentException.class, () -> reservationService.deleteReservation(1L));
    }

    @Test
    void testGetReservationsByUserId_Success() {


        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(reservationRepository.findByUser(Optional.of(user))).thenReturn(Collections.singletonList(new Reservation()));

        List<ReservationResponseDTO> responseDTOs = reservationService.getReservationsByUserId(1L);

        assertFalse(responseDTOs.isEmpty());
    }

    @Test
    void testGetReservationsByUserId_InvalidUserId() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> reservationService.getReservationsByUserId(1L));
    }

    @Test
    void testGetAllReservations() {
        when(reservationRepository.findAll()).thenReturn(Collections.singletonList(new Reservation()));

        List<ReservationResponseDTO> responseDTOs = reservationService.getAllReservations();

        assertFalse(responseDTOs.isEmpty());
    }
}