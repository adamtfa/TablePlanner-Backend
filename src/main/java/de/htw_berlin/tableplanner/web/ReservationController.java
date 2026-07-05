package de.htw_berlin.tableplanner.web;

import de.htw_berlin.tableplanner.model.Reservation;
import de.htw_berlin.tableplanner.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class ReservationController {
    private final ReservationService service;

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation,
                                                         @RequestParam Long customerId,
                                                         @RequestParam Long tableId) {
        Reservation created = service.save(reservation, customerId, tableId);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/reservations/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id,
                                                         @RequestBody Reservation reservation,
                                                         @RequestParam Long customerId,
                                                         @RequestParam Long tableId) {
        return ResponseEntity.ok(service.update(id, reservation, customerId, tableId));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}