package com.vriddhi.tablebookingapp.controller;

import com.vriddhi.tablebookingapp.model.Restaurant;
import com.vriddhi.tablebookingapp.model.Table;
import com.vriddhi.tablebookingapp.service.TableService;
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
    public List<Table> getTables(@PathVariable Long restaurantId) {
        return tableService.getTablesByRestaurantId(restaurantId);
    }

    @GetMapping("/{tableId}")
    public ResponseEntity<Table> getTable(@PathVariable Long tableId) {
        Optional<Table> table = tableService.getTableById(tableId);
        return table.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Table> createTable(@PathVariable Long restaurantId, @RequestBody Table table) {
        table.setRestaurant(new Restaurant());
        table.getRestaurant().setRestaurantId(restaurantId);
        Table newTable = tableService.saveTable(table);
        return ResponseEntity.ok(newTable);
    }

    @PutMapping("/{tableId}")
    public ResponseEntity<Table> updateTable(@PathVariable Long tableId, @RequestBody Table table) {
        table.setTableId(tableId);
        Table updatedTable = tableService.saveTable(table);
        return ResponseEntity.ok(updatedTable);
    }

    @DeleteMapping("/{tableId}")
    public ResponseEntity<Void> deleteTable(@PathVariable Long tableId) {
        tableService.deleteTable(tableId);
        return ResponseEntity.noContent().build();
    }
}
