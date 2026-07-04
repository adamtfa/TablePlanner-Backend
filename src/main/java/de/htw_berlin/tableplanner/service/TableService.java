package de.htw_berlin.tableplanner.service;

import de.htw_berlin.tableplanner.exception.ResourceNotFoundException;
import de.htw_berlin.tableplanner.model.RestaurantTable;
import de.htw_berlin.tableplanner.repository.ReservationRepository;
import de.htw_berlin.tableplanner.repository.RestaurantTableRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class TableService {
    private static final ZoneId RESTAURANT_ZONE = ZoneId.of("Europe/Berlin");
    private static final int RESERVATION_DURATION_MINUTES = 120;

    private final RestaurantTableRepository repo;
    private final ReservationRepository reservationRepo;

    public List<RestaurantTable> getAll() {
        List<RestaurantTable> tables = repo.findAll();
        tables.forEach(this::applyCurrentAvailability);
        return tables;
    }

    public RestaurantTable getById(Long id) {
        RestaurantTable table = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table not found with id " + id));
        applyCurrentAvailability(table);
        return table;
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

    private void applyCurrentAvailability(RestaurantTable table) {
        table.setAvailable(!isCurrentlyOccupied(table.getId()));
    }

    private boolean isCurrentlyOccupied(Long tableId) {
        LocalDateTime now = LocalDateTime.now(RESTAURANT_ZONE);
        return reservationRepo.findByTable_Id(tableId).stream()
                .anyMatch(r -> {
                    LocalDateTime start = LocalDateTime.of(r.getDate(), r.getTime());
                    LocalDateTime end = start.plusMinutes(RESERVATION_DURATION_MINUTES);
                    return !now.isBefore(start) && now.isBefore(end);
                });
    }
}