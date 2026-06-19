package de.htw_berlin.tableplanner.service;

import de.htw_berlin.tableplanner.model.Customer;
import de.htw_berlin.tableplanner.model.Reservation;
import de.htw_berlin.tableplanner.model.RestaurantTable;
import de.htw_berlin.tableplanner.repository.CustomerRepository;
import de.htw_berlin.tableplanner.repository.ReservationRepository;
import de.htw_berlin.tableplanner.repository.RestaurantTableRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return repo.findById(id).orElseThrow(RuntimeException::new);
    }

    public Reservation save(Reservation reservation, Long customerId, Long tableId) {
        Customer customer = customerRepo.findById(customerId).orElseThrow(RuntimeException::new);
        reservation.setCustomer(customer);

        RestaurantTable table = tableRepo.findById(tableId).orElseThrow(RuntimeException::new);
        reservation.setTable(table);

        return repo.save(reservation);
    }

    public Reservation update(Long id, Reservation updatedReservation, Long customerId, Long tableId) {
        Reservation existing = repo.findById(id).orElseThrow(RuntimeException::new);

        Customer customer = customerRepo.findById(customerId).orElseThrow(RuntimeException::new);
        existing.setCustomer(customer);

        RestaurantTable table = tableRepo.findById(tableId).orElseThrow(RuntimeException::new);
        existing.setTable(table);

        existing.setDate(updatedReservation.getDate());
        existing.setTime(updatedReservation.getTime());
        existing.setNumberOfGuests(updatedReservation.getNumberOfGuests());
        return repo.save(existing);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
