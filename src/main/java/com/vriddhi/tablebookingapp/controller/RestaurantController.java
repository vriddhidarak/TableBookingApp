package com.vriddhi.tablebookingapp.controller;

import com.vriddhi.tablebookingapp.dto.RestaurantResponseDTO;
import com.vriddhi.tablebookingapp.model.Restaurant;
import com.vriddhi.tablebookingapp.model.Table;
import com.vriddhi.tablebookingapp.service.RestaurantServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantServiceInterface restaurantService;

    @GetMapping
    @Operation(summary = "Get all restaurants", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved restaurants",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Restaurant.class))})
    })
    public List<RestaurantResponseDTO> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/{restaurantId}")
    @Operation(summary = "Get by restaurant Id", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved restaurant",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Table.class))})
    })
    public ResponseEntity<RestaurantResponseDTO> getRestaurant(@PathVariable Long restaurantId) {
        Optional<RestaurantResponseDTO> restaurant = restaurantService.getRestaurantById(restaurantId);
        return restaurant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "create restaurant", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully created restaurant",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Restaurant.class))})
    })
    public ResponseEntity<RestaurantResponseDTO> createRestaurant(@RequestBody Restaurant restaurant) {
        RestaurantResponseDTO newRestaurant = restaurantService.saveRestaurant(restaurant);
        return ResponseEntity.ok(newRestaurant);
    }

    @PutMapping("/{restaurantId}")
    @Operation(summary = "Update restaurant", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully updated restaurant",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Restaurant.class))})
    })
    public ResponseEntity<RestaurantResponseDTO> updateRestaurant(@PathVariable Long restaurantId, @RequestBody Restaurant restaurant) {
        restaurant.setRestaurantId(restaurantId);
        RestaurantResponseDTO updatedRestaurant = restaurantService.saveRestaurant(restaurant);
        return ResponseEntity.ok(updatedRestaurant);
    }

    @DeleteMapping("/{restaurantId}")
    @Operation(summary = "Delete restaurant", responses = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted restaurant")
    })
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long restaurantId) {
        restaurantService.deleteRestaurant(restaurantId);
        return ResponseEntity.noContent().build();
    }
}
