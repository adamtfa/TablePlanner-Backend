package de.htw_berlin.tableplanner.repository;

import de.htw_berlin.tableplanner.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findByName(String name);
    Optional<Restaurant> findByEmail(String email);
    boolean existsByName(String name);
    boolean existsByEmail(String email);
}