package de.htw_berlin.tableplanner.repository;

import de.htw_berlin.tableplanner.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
}
