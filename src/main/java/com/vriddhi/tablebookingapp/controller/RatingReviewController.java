package com.vriddhi.tablebookingapp.controller;

import com.vriddhi.tablebookingapp.dto.RatingReviewRequestDTO;
import com.vriddhi.tablebookingapp.dto.RatingReviewResponseDTO;
import com.vriddhi.tablebookingapp.model.Table;
import com.vriddhi.tablebookingapp.service.RatingReviewServiceInterface;
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
@RequestMapping("/api/ratings-reviews")
public class RatingReviewController {

    @Autowired
    private RatingReviewServiceInterface ratingReviewService;

    @GetMapping
    @Operation(summary = "Get all Rating Review", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved Reviews",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Table.class))})
    })
    public List<RatingReviewResponseDTO> getAllRatingReviews() {
        return ratingReviewService.getAllRatingReviews();
    }
    @PostMapping
    @Operation(summary = "create a rating review", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully created Reviews",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Table.class))})
    })
    public ResponseEntity<RatingReviewResponseDTO> createRatingReview(@RequestBody RatingReviewRequestDTO ratingReview) {
        RatingReviewResponseDTO newRatingReview = ratingReviewService.saveRatingReview(ratingReview);
        return ResponseEntity.ok(newRatingReview);
    }

    @GetMapping("/{ratingId}")
    @Operation(summary = "Get by Rating Id", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved Rating",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Table.class))})
    })
    public ResponseEntity<RatingReviewResponseDTO> getRatingReview(@PathVariable Long ratingId) {
        Optional<RatingReviewResponseDTO> ratingReview = ratingReviewService.getRatingReviewById(ratingId);
        return ratingReview.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/restaurant/{restaurantId}")
    @Operation(summary = "Get by Restaurant Id", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved Rating",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Table.class))})
    })
    public List<RatingReviewResponseDTO> getRatingsReviewsByRestaurant(@PathVariable Long restaurantId) {
        return ratingReviewService.getRatingsReviewsByRestaurantId(restaurantId);
    }

    @DeleteMapping("/{ratingId}")
    @Operation(summary = "Delete Rating Review", responses = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted Rating Review")
    })
    public ResponseEntity<Void> deleteRatingReview(@PathVariable Long ratingId) {
        ratingReviewService.deleteRatingReview(ratingId);
        return ResponseEntity.noContent().build();
    }
}
