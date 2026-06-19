package de.htw_berlin.tableplanner.web;

import lombok.AllArgsConstructor;
import de.htw_berlin.tableplanner.model.RestaurantTable;
import de.htw_berlin.tableplanner.service.TableService;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/tables/{id}")
    public RestaurantTable updateTable(@PathVariable Long id, @RequestBody RestaurantTable table) {
        return service.update(id, table);
    }

    @DeleteMapping("/tables/{id}")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }
}