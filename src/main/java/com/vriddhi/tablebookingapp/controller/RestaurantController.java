package com.vriddhi.tablebookingapp.controller;

import com.vriddhi.tablebookingapp.model.Restaurant;
import com.vriddhi.tablebookingapp.model.Table;
import com.vriddhi.tablebookingapp.service.RestaurantService;
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
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/{restaurantId}")
    @Operation(summary = "Get by restaurant Id", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved restaurant",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Table.class))})
    })
    public ResponseEntity<Restaurant> getRestaurant(@PathVariable Long restaurantId) {
        Optional<Restaurant> restaurant = restaurantService.getRestaurantById(restaurantId);
        return restaurant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "create restaurant", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully created restaurant",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Restaurant.class))})
    })
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
        Restaurant newRestaurant = restaurantService.saveRestaurant(restaurant);
        return ResponseEntity.ok(newRestaurant);
    }

    @PutMapping("/{restaurantId}")
    @Operation(summary = "Update restaurant", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully updated restaurant",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Restaurant.class))})
    })
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Long restaurantId, @RequestBody Restaurant restaurant) {
        restaurant.setRestaurantId(restaurantId);
        Restaurant updatedRestaurant = restaurantService.saveRestaurant(restaurant);
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
