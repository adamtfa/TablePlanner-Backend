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

    public RestaurantTable update(Long id, RestaurantTable updatedTable) {
        RestaurantTable existing = repo.findById(id).orElseThrow(RuntimeException::new);
        existing.setTableNumber(updatedTable.getTableNumber());
        existing.setCapacity(updatedTable.getCapacity());
        existing.setAvailable(updatedTable.isAvailable());
        return repo.save(existing);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}