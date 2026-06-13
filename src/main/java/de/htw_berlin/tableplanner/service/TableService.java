package de.htw_berlin.tableplanner.service;

import de.htw_berlin.tableplanner.model.RestaurantTable;
import de.htw_berlin.tableplanner.repository.RestaurantTableRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TableService {
    private final RestaurantTableRepository repo;

    public List<RestaurantTable> getAll() {
        return repo.findAll();
    }

    public RestaurantTable getById(Long id) {
        return repo.findById(id).orElseThrow(RuntimeException::new);
    }

    public RestaurantTable save(RestaurantTable table) {
        return repo.save(table);
    }

    public void deleteAll() {
        repo.deleteAll();
    }
}