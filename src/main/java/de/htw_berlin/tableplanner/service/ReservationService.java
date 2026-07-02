package de.htw_berlin.tableplanner.service;

import de.htw_berlin.tableplanner.exception.ResourceNotFoundException;
import de.htw_berlin.tableplanner.model.Customer;
import de.htw_berlin.tableplanner.model.Reservation;
import de.htw_berlin.tableplanner.model.RestaurantTable;
import de.htw_berlin.tableplanner.repository.CustomerRepository;
import de.htw_berlin.tableplanner.repository.ReservationRepository;
import de.htw_berlin.tableplanner.repository.RestaurantTableRepository;
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

    public List<Reservation> getAll() {
        return repo.findAll();
    }

    public Reservation getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id " + id));
    }

    public Reservation save(Reservation reservation, Long customerId, Long tableId) {
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + customerId));
        reservation.setCustomer(customer);

        RestaurantTable table = tableRepo.findById(tableId)
                .orElseThrow(() -> new ResourceNotFoundException("Table not found with id " + tableId));
        reservation.setTable(table);

        log.info("Creating reservation for customer {} and table {}", customerId, tableId);
        return repo.save(reservation);
    }

    public Reservation update(Long id, Reservation updatedReservation, Long customerId, Long tableId) {
        Reservation existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id " + id));

        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + customerId));
        existing.setCustomer(customer);

        RestaurantTable table = tableRepo.findById(tableId)
                .orElseThrow(() -> new ResourceNotFoundException("Table not found with id " + tableId));
        existing.setTable(table);

        existing.setDate(updatedReservation.getDate());
        existing.setTime(updatedReservation.getTime());
        existing.setNumberOfGuests(updatedReservation.getNumberOfGuests());

        log.info("Updating reservation {}", id);
        return repo.save(existing);
    }

    public void deleteById(Long id) {
        log.info("Deleting reservation {}", id);
        repo.deleteById(id);
    }
}
