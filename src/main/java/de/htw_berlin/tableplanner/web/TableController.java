package de.htw_berlin.tableplanner.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import de.htw_berlin.tableplanner.model.RestaurantTable;

@RestController
public class TableController {

    @GetMapping("/tables")
    public ResponseEntity<List<RestaurantTable>> getTables() {
        List<RestaurantTable> tables = List.of(
            new RestaurantTable(1, 2, true),
            new RestaurantTable(2, 4, false),
            new RestaurantTable(3, 6, true)
        );
        return ResponseEntity.ok(tables);
    }
}
