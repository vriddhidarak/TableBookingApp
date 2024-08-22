package com.vriddhi.tablebookingapp;

import com.vriddhi.tablebookingapp.dto.RatingReviewDTO;
import com.vriddhi.tablebookingapp.model.RatingReview;
import com.vriddhi.tablebookingapp.model.Restaurant;
import com.vriddhi.tablebookingapp.model.User;
import com.vriddhi.tablebookingapp.repository.RatingReviewRepository;
import com.vriddhi.tablebookingapp.repository.RestaurantRepository;
import com.vriddhi.tablebookingapp.repository.UserRepository;
import com.vriddhi.tablebookingapp.service.RatingReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RatingReviewServiceTest {

    @Mock
    private RatingReviewRepository ratingReviewRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RatingReviewService ratingReviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveRatingReview_Success() {
        RatingReviewDTO ratingReviewDTO = new RatingReviewDTO();
        ratingReviewDTO.setRestaurantId(1L);
        ratingReviewDTO.setUserId(1L);
        ratingReviewDTO.setRating(5);
        ratingReviewDTO.setReview("Great!");

        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(1L);

        User user = new User();
        user.setUserId(1L);

        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(ratingReviewRepository.save(any(RatingReview.class))).thenReturn(new RatingReview());

        RatingReview savedRatingReview = ratingReviewService.saveRatingReview(ratingReviewDTO);

        assertNotNull(savedRatingReview);
        verify(ratingReviewRepository, times(1)).save(any(RatingReview.class));
    }

    @Test
    void testSaveRatingReview_InvalidRestaurantId() {
        RatingReviewDTO ratingReviewDTO = new RatingReviewDTO();
        ratingReviewDTO.setRestaurantId(1L);

        when(restaurantRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> ratingReviewService.saveRatingReview(ratingReviewDTO));
    }

    @Test
    void testSaveRatingReview_InvalidUserId() {
        RatingReviewDTO ratingReviewDTO = new RatingReviewDTO();
        ratingReviewDTO.setRestaurantId(1L);
        ratingReviewDTO.setUserId(1L);

        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(1L);

        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> ratingReviewService.saveRatingReview(ratingReviewDTO));
    }

    @Test
    void testGetRatingReviewById_Success() {
        RatingReview ratingReview = new RatingReview();
        ratingReview.setRatingId(1L);

        when(ratingReviewRepository.findById(1L)).thenReturn(Optional.of(ratingReview));

        Optional<RatingReview> fetchedRatingReview = ratingReviewService.getRatingReviewById(1L);

        assertTrue(fetchedRatingReview.isPresent());
        assertEquals(1L, fetchedRatingReview.get().getRatingId());
    }

    @Test
    void testGetRatingReviewById_NotFound() {
        when(ratingReviewRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<RatingReview> fetchedRatingReview = ratingReviewService.getRatingReviewById(1L);

        assertFalse(fetchedRatingReview.isPresent());
    }

    @Test
    void testDeleteRatingReview_Success() {
        doNothing().when(ratingReviewRepository).deleteById(1L);

        ratingReviewService.deleteRatingReview(1L);

        verify(ratingReviewRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteRatingReview_NotFound() {
        doThrow(new IllegalArgumentException("Invalid rating review ID")).when(ratingReviewRepository).deleteById(1L);

        assertThrows(IllegalArgumentException.class, () -> ratingReviewService.deleteRatingReview(1L));
    }

    @Test
    void testGetRatingsReviewsByRestaurantId_Success() {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(1L);

        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(ratingReviewRepository.findByRestaurant(Optional.of(restaurant))).thenReturn(Collections.singletonList(new RatingReview()));

        List<RatingReview> ratingReviews = ratingReviewService.getRatingsReviewsByRestaurantId(1L);

        assertFalse(ratingReviews.isEmpty());
    }

    @Test
    void testGetRatingsReviewsByRestaurantId_InvalidRestaurantId() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.empty());

        List<RatingReview> ratingReviews = ratingReviewService.getRatingsReviewsByRestaurantId(1L);

        assertTrue(ratingReviews.isEmpty());
    }

    @Test
    void testGetAllRatingReviews() {
        when(ratingReviewRepository.findAll()).thenReturn(Collections.singletonList(new RatingReview()));

        List<RatingReview> ratingReviews = ratingReviewService.getAllRatingReviews();

        assertFalse(ratingReviews.isEmpty());
    }
}