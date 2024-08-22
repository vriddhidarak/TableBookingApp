package com.vriddhi.tablebookingapp.service;

import com.vriddhi.tablebookingapp.dto.RatingReviewDTO;
import com.vriddhi.tablebookingapp.dto.ReservationDTO;
import com.vriddhi.tablebookingapp.dto.RestaurantResponse1DTO;
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
    public List<RestaurantResponse1DTO> getAllRestaurants() {

        return restaurantRepository.findAll().stream().map(this::mapToRestaurantResponseDTO).toList();
    }

    @Override
    public Optional<RestaurantResponse1DTO> getRestaurantById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId).map(this::mapToRestaurantResponseDTO);
    }

    @Override
    @Transactional
    public RestaurantResponse1DTO saveRestaurant(Restaurant restaurant) {
        restaurant.setRestaurantTotalTableCount(0);
        return mapToRestaurantResponseDTO(restaurantRepository.save(restaurant));
    }

    @Override
    @Transactional
    public void deleteRestaurant(Long restaurantId) {
        restaurantRepository.deleteById(restaurantId);
    }

    private RestaurantResponse1DTO mapToRestaurantResponseDTO(Restaurant restaurant) {
        RestaurantResponse1DTO restaurantResponse1DTO = new RestaurantResponse1DTO();
        restaurantResponse1DTO.setRestaurantId(restaurant.getRestaurantId());
        restaurantResponse1DTO.setRestaurantName(restaurant.getRestaurantName());
        restaurantResponse1DTO.setRestaurantLocation(restaurant.getRestaurantLocation());
        restaurantResponse1DTO.setRestaurantDescription(restaurant.getRestaurantDescription());
        restaurantResponse1DTO.setRestaurantTotalTableCount(restaurant.getRestaurantTotalTableCount());
        List<TableResponseDTO> tables = new ArrayList<>();
        for (Table table : restaurant.getTables()) {
            tables.add(maptoTableResponseDTO(table));
        }
        restaurantResponse1DTO.setTables(tables);
        List<ReservationDTO> reservations = new ArrayList<>();
        for(Reservation reservation: restaurant.getReservations()){
            reservations.add(maptoReservationDTO(reservation));
        }
        restaurantResponse1DTO.setReservations(reservations);
        List<RatingReviewDTO> ratingReviewDTOS = new ArrayList<>();
        for(RatingReview ratingReview: restaurant.getRatingReviews()){
            ratingReviewDTOS.add(maptoRatingReviewDTO(ratingReview));
        }
        restaurantResponse1DTO.setRatingReviews(ratingReviewDTOS);
        return restaurantResponse1DTO;
    }

    private TableResponseDTO maptoTableResponseDTO(Table table){
        TableResponseDTO tableResponseDTO = new TableResponseDTO();
        tableResponseDTO.setTableId(table.getTableId());
        tableResponseDTO.setTableNumber(table.getTableNumber());
        tableResponseDTO.setTotalSeats(table.getTotalSeats());
        return tableResponseDTO;
    }

    private ReservationDTO maptoReservationDTO(Reservation reservation){
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setUserId(reservation.getUser().getUserId());
        reservationDTO.setTableId(reservation.getTable().getTableId());
        reservationDTO.setRestaurantId(reservation.getRestaurant().getRestaurantId());
        reservationDTO.setReservationDateTime(reservation.getReservationDateTime());
        reservationDTO.setPartySize(reservation.getPartySize());
        return reservationDTO;
    }

    private RatingReviewDTO maptoRatingReviewDTO(RatingReview ratingReview){
        RatingReviewDTO ratingReviewDTO = new RatingReviewDTO();
        ratingReviewDTO.setRestaurantId(ratingReview.getRestaurant().getRestaurantId());
        ratingReviewDTO.setRating(ratingReview.getRating());
        ratingReviewDTO.setReview(ratingReview.getReview());
        return ratingReviewDTO;
    }
}
