package de.htw_berlin.tableplanner.web;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import de.htw_berlin.tableplanner.service.TableService;
import de.htw_berlin.tableplanner.model.RestaurantTable;

import java.util.List;

@RestController
@AllArgsConstructor
public class TableController {
    private final TableService service;

    @GetMapping("/tables")
    public List<RestaurantTable> getAllTables() {
        return service.getAll();
    }

    @GetMapping("/tables/{id}")
    public RestaurantTable getTableById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/tables")
    public RestaurantTable createTable(@RequestBody RestaurantTable table) {
        return service.save(table);
    }

    @DeleteMapping("/tables")
    public void deleteAll() {
        service.deleteAll();
    }
}