package de.htw_berlin.tableplanner.repository;

import de.htw_berlin.tableplanner.model.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable,Long> {
}
