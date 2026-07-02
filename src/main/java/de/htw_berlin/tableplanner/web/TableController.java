package de.htw_berlin.tableplanner.web;

import lombok.AllArgsConstructor;
import de.htw_berlin.tableplanner.model.RestaurantTable;
import de.htw_berlin.tableplanner.service.TableService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class TableController {
    private final TableService service;

    @GetMapping("/tables")
    public ResponseEntity<List<RestaurantTable>> getAllTables() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/tables/{id}")
    public ResponseEntity<RestaurantTable> getTableById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/tables")
    public ResponseEntity<RestaurantTable> createTable(@RequestBody RestaurantTable table) {
        RestaurantTable created = service.save(table);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/tables/{id}")
    public ResponseEntity<RestaurantTable> updateTable(@PathVariable Long id, @RequestBody RestaurantTable table) {
        return ResponseEntity.ok(service.update(id, table));
    }

    @DeleteMapping("/tables/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}