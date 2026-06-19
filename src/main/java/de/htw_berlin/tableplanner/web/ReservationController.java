package de.htw_berlin.tableplanner.web;

import lombok.AllArgsConstructor;
import de.htw_berlin.tableplanner.model.Reservation;
import de.htw_berlin.tableplanner.service.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class ReservationController {
    private final ReservationService service;

    @GetMapping("/reservations")
    public List<Reservation> getAllReservations() {
        return service.getAll();
    }

    @GetMapping("/reservations/{id}")
    public Reservation getReservationById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/reservations")
    public Reservation createReservation(@RequestBody Reservation reservation, @RequestParam Long customerId, @RequestParam Long tableId) {
        return service.save(reservation, customerId, tableId);
    }

    @PutMapping("/reservations/{id}")
    public Reservation updateReservation(@PathVariable Long id, @RequestBody Reservation reservation, @RequestParam Long customerId, @RequestParam Long tableId) {
        return service.update(id, reservation, customerId, tableId);
    }

    @DeleteMapping("/reservations/{id}")
    public void deleteReservation(@PathVariable Long id) {
        service.deleteById(id);
    }
}
