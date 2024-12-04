package com.vriddhi.tablebookingapp.controller;

import com.vriddhi.tablebookingapp.dto.ReservationRequestDTO;
import com.vriddhi.tablebookingapp.dto.ReservationResponseDTO;
import com.vriddhi.tablebookingapp.model.Reservation;
import com.vriddhi.tablebookingapp.model.Table;
import com.vriddhi.tablebookingapp.service.ReservationServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    private ReservationServiceInterface reservationService;

    @GetMapping
    @Operation(summary = "Get all Reservation", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved Reservation",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Table.class))})
    })
    public List<ReservationResponseDTO> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @PostMapping
    @Operation(summary = "Create Reservation", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully created Reservation",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Reservation.class))})
    })
    public ResponseEntity<ReservationResponseDTO> createReservation(@RequestBody ReservationRequestDTO reservation) {
        ReservationResponseDTO newReservation = reservationService.saveReservation(reservation);
        return ResponseEntity.ok(newReservation);
    }

    @GetMapping("/{reservationId}")
    @Operation(summary = "Get by Reservation Id", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved Reservation",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Table.class))})
    })
    public ResponseEntity<ReservationResponseDTO> getReservation(@PathVariable Long reservationId) {
        Optional<ReservationResponseDTO> reservation = reservationService.getReservationById(reservationId);

        return reservation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get by User Id", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved Reservation",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Table.class))})
    })
    public List<ReservationResponseDTO> getReservationsByUser(@PathVariable Long userId) {
        return reservationService.getReservationsByUserId(userId);
    }

    @RequestMapping(path="/{reservationId}", method = RequestMethod.DELETE)
    @Operation(summary = "Delete Reservation", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted Reservation")
    })
    public ResponseEntity<Void> deleteReservation(@PathVariable Long reservationId) {
        log.info("ReservationController.deleteReservation  {}", reservationId);
        reservationService.deleteReservation(reservationId);
        return ResponseEntity.noContent().build();
    }
}
