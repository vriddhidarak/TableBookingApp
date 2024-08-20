package com.vriddhi.tablebookingapp.controller;

import com.vriddhi.tablebookingapp.model.Restaurant;
import com.vriddhi.tablebookingapp.model.Table;
import com.vriddhi.tablebookingapp.model.User;
import com.vriddhi.tablebookingapp.service.TableService;
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
@RequestMapping("/api/restaurants/{restaurantId}/tables")
public class TableController {

    @Autowired
    private TableService tableService;

    @GetMapping
    @Operation(summary = "Get all Tables", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved Tables",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Table.class))})
    })
    public List<Table> getTables(@PathVariable Long restaurantId) {
        return tableService.getTablesByRestaurantId(restaurantId);
    }

    @GetMapping("/{tableId}")
    @Operation(summary = "Get by Table Id", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved Table",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Table.class))})
    })
    public ResponseEntity<Table> getTable(@PathVariable Long tableId) {
        Optional<Table> table = tableService.getTableById(tableId);
        return table.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "create table", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully created table",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Table.class))})
    })
    public ResponseEntity<Table> createTable(@PathVariable Long restaurantId, @RequestBody Table table) {
        table.setRestaurant(new Restaurant());
        table.getRestaurant().setRestaurantId(restaurantId);
        Table newTable = tableService.saveTable(table,restaurantId);
        return ResponseEntity.ok(newTable);
    }

    @PutMapping("/{tableId}")
    @Operation(summary = "Update Table", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully updated Table",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Table.class))})
    })
    public ResponseEntity<Table> updateTable(@PathVariable Long restaurantId,@PathVariable Long tableId, @RequestBody Table table) {
        table.setTableId(tableId);
        Table updatedTable = tableService.saveTable(table,restaurantId);
        return ResponseEntity.ok(updatedTable);
    }

    @DeleteMapping("/{tableId}")
    @Operation(summary = "Delete Table", responses = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted Table")
    })
    public ResponseEntity<Void> deleteTable(@PathVariable Long tableId) {
        tableService.deleteTable(tableId);
        return ResponseEntity.noContent().build();
    }
}
