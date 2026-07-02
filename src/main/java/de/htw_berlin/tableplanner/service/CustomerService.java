package de.htw_berlin.tableplanner.service;

import de.htw_berlin.tableplanner.exception.ResourceNotFoundException;
import de.htw_berlin.tableplanner.model.Customer;
import de.htw_berlin.tableplanner.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository repo;

    public List<Customer> getAll() {
        return repo.findAll();
    }

    public Customer getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + id));
    }

    public Customer save(Customer customer) {
        log.info("Creating customer {} {}", customer.getFirstName(), customer.getLastName());
        return repo.save(customer);
    }

    public Customer update(Long id, Customer updatedCustomer) {
        Customer existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + id));
        existing.setFirstName(updatedCustomer.getFirstName());
        existing.setLastName(updatedCustomer.getLastName());
        existing.setPhoneNumber(updatedCustomer.getPhoneNumber());

        log.info("Updating customer {}", id);
        return repo.save(existing);
    }

    public void deleteById(Long id) {
        log.info("Deleting customer {}", id);
        repo.deleteById(id);
    }
}