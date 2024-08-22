package com.vriddhi.tablebookingapp.service;

import com.vriddhi.tablebookingapp.dto.RatingReviewRequestDTO;
import com.vriddhi.tablebookingapp.dto.ReservationRequestDTO;
import com.vriddhi.tablebookingapp.dto.RestaurantResponseDTO;
import com.vriddhi.tablebookingapp.dto.TableResponseDTO;
import com.vriddhi.tablebookingapp.model.RatingReview;
import com.vriddhi.tablebookingapp.model.Reservation;
import com.vriddhi.tablebookingapp.model.Restaurant;
import com.vriddhi.tablebookingapp.model.Table;
import com.vriddhi.tablebookingapp.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService implements RestaurantServiceInterface {

    @Autowired
    private RestaurantRepository restaurantRepository;


    @Override
    public List<RestaurantResponseDTO> getAllRestaurants() {

        return restaurantRepository.findAll().stream().map(this::mapToRestaurantResponseDTO).toList();
    }

    @Override
    public Optional<RestaurantResponseDTO> getRestaurantById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId).map(this::mapToRestaurantResponseDTO);
    }

    @Override
    @Transactional
    public RestaurantResponseDTO saveRestaurant(Restaurant restaurant) {
        restaurant.setRestaurantTotalTableCount(0);
        return mapToRestaurantResponseDTO(restaurantRepository.save(restaurant));
    }

    @Override
    @Transactional
    public void deleteRestaurant(Long restaurantId) {
        restaurantRepository.deleteById(restaurantId);
    }

    private RestaurantResponseDTO mapToRestaurantResponseDTO(Restaurant restaurant) {
        RestaurantResponseDTO restaurantResponseDTO = new RestaurantResponseDTO();
        restaurantResponseDTO.setRestaurantId(restaurant.getRestaurantId());
        restaurantResponseDTO.setRestaurantName(restaurant.getRestaurantName());
        restaurantResponseDTO.setRestaurantLocation(restaurant.getRestaurantLocation());
        restaurantResponseDTO.setRestaurantDescription(restaurant.getRestaurantDescription());
        restaurantResponseDTO.setRestaurantTotalTableCount(restaurant.getRestaurantTotalTableCount());
        List<TableResponseDTO> tables = new ArrayList<>();
        for (Table table : restaurant.getTables()) {
            tables.add(maptoTableResponseDTO(table));
        }
        restaurantResponseDTO.setTables(tables);
        List<ReservationRequestDTO> reservations = new ArrayList<>();
        for(Reservation reservation: restaurant.getReservations()){
            reservations.add(maptoReservationDTO(reservation));
        }
        restaurantResponseDTO.setReservations(reservations);
        List<RatingReviewRequestDTO> ratingReviewRequestDTOS = new ArrayList<>();
        for(RatingReview ratingReview: restaurant.getRatingReviews()){
            ratingReviewRequestDTOS.add(maptoRatingReviewDTO(ratingReview));
        }
        restaurantResponseDTO.setRatingReviews(ratingReviewRequestDTOS);
        return restaurantResponseDTO;
    }

    private TableResponseDTO maptoTableResponseDTO(Table table){
        TableResponseDTO tableResponseDTO = new TableResponseDTO();
        tableResponseDTO.setTableId(table.getTableId());
        tableResponseDTO.setTableNumber(table.getTableNumber());
        tableResponseDTO.setTotalSeats(table.getTotalSeats());
        return tableResponseDTO;
    }

    private ReservationRequestDTO maptoReservationDTO(Reservation reservation){
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO();
        reservationRequestDTO.setUserId(reservation.getUser().getUserId());
        reservationRequestDTO.setTableId(reservation.getTable().getTableId());
        reservationRequestDTO.setRestaurantId(reservation.getRestaurant().getRestaurantId());
        reservationRequestDTO.setReservationDateTime(reservation.getReservationDateTime());
        reservationRequestDTO.setPartySize(reservation.getPartySize());
        return reservationRequestDTO;
    }

    private RatingReviewRequestDTO maptoRatingReviewDTO(RatingReview ratingReview){
        RatingReviewRequestDTO ratingReviewRequestDTO = new RatingReviewRequestDTO();
        ratingReviewRequestDTO.setRestaurantId(ratingReview.getRestaurant().getRestaurantId());
        ratingReviewRequestDTO.setRating(ratingReview.getRating());
        ratingReviewRequestDTO.setReview(ratingReview.getReview());
        return ratingReviewRequestDTO;
    }
}
