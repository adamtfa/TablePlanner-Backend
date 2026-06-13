package de.htw_berlin.tableplanner.repository;

import de.htw_berlin.tableplanner.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
