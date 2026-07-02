package de.htw_berlin.tableplanner.service;

import de.htw_berlin.tableplanner.exception.ResourceNotFoundException;
import de.htw_berlin.tableplanner.model.RestaurantTable;
import de.htw_berlin.tableplanner.repository.RestaurantTableRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class TableService {
    private final RestaurantTableRepository repo;

    public List<RestaurantTable> getAll() {
        return repo.findAll();
    }

    public RestaurantTable getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table not found with id " + id));
    }

    public RestaurantTable save(RestaurantTable table) {
        log.info("Creating table number {}", table.getTableNumber());
        return repo.save(table);
    }

    public RestaurantTable update(Long id, RestaurantTable updatedTable) {
        RestaurantTable existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table not found with id " + id));
        existing.setTableNumber(updatedTable.getTableNumber());
        existing.setCapacity(updatedTable.getCapacity());
        existing.setAvailable(updatedTable.isAvailable());

        log.info("Updating table {}", id);
        return repo.save(existing);
    }

    public void deleteById(Long id) {
        log.info("Deleting table {}", id);
        repo.deleteById(id);
    }
}