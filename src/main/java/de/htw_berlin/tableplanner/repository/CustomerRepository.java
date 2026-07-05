package de.htw_berlin.tableplanner.repository;

import de.htw_berlin.tableplanner.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByRestaurant_Id(Long restaurantId);
}