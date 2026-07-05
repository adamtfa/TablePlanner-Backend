package de.htw_berlin.tableplanner.service;

import de.htw_berlin.tableplanner.exception.ResourceNotFoundException;
import de.htw_berlin.tableplanner.model.Customer;
import de.htw_berlin.tableplanner.model.Reservation;
import de.htw_berlin.tableplanner.model.RestaurantTable;
import de.htw_berlin.tableplanner.repository.CustomerRepository;
import de.htw_berlin.tableplanner.repository.ReservationRepository;
import de.htw_berlin.tableplanner.repository.RestaurantRepository;
import de.htw_berlin.tableplanner.repository.RestaurantTableRepository;
import de.htw_berlin.tableplanner.security.CurrentRestaurant;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ReservationService {
    private final ReservationRepository repo;
    private final CustomerRepository customerRepo;
    private final RestaurantTableRepository tableRepo;
    private final RestaurantRepository restaurantRepo;
    private final CurrentRestaurant currentRestaurant;

    public List<Reservation> getAll() {
        return repo.findByRestaurant_Id(currentRestaurant.getId());
    }

    public Reservation getById(Long id) {
        return repo.findById(id)
                .filter(this::belongsToCurrentRestaurant)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id " + id));
    }

    public Reservation save(Reservation reservation, Long customerId, Long tableId) {
        reservation.setCustomer(findOwnedCustomer(customerId));
        reservation.setTable(findOwnedTable(tableId));
        reservation.setRestaurant(restaurantRepo.getReferenceById(currentRestaurant.getId()));

        log.info("Creating reservation for customer {} and table {}", customerId, tableId);
        return repo.save(reservation);
    }

    public Reservation update(Long id, Reservation updatedReservation, Long customerId, Long tableId) {
        Reservation existing = repo.findById(id)
                .filter(this::belongsToCurrentRestaurant)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id " + id));

        existing.setCustomer(findOwnedCustomer(customerId));
        existing.setTable(findOwnedTable(tableId));

        existing.setDate(updatedReservation.getDate());
        existing.setTime(updatedReservation.getTime());
        existing.setNumberOfGuests(updatedReservation.getNumberOfGuests());

        log.info("Updating reservation {}", id);
        return repo.save(existing);
    }

    public void deleteById(Long id) {
        Reservation existing = repo.findById(id)
                .filter(this::belongsToCurrentRestaurant)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id " + id));
        log.info("Deleting reservation {}", id);
        repo.deleteById(existing.getId());
    }

    private Customer findOwnedCustomer(Long customerId) {
        return customerRepo.findById(customerId)
                .filter(c -> c.getRestaurant() != null && c.getRestaurant().getId().equals(currentRestaurant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + customerId));
    }

    private RestaurantTable findOwnedTable(Long tableId) {
        return tableRepo.findById(tableId)
                .filter(t -> t.getRestaurant() != null && t.getRestaurant().getId().equals(currentRestaurant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Table not found with id " + tableId));
    }

    private boolean belongsToCurrentRestaurant(Reservation reservation) {
        return reservation.getRestaurant() != null
                && reservation.getRestaurant().getId().equals(currentRestaurant.getId());
    }
}