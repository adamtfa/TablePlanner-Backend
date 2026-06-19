package de.htw_berlin.tableplanner.service;

import de.htw_berlin.tableplanner.model.Customer;
import de.htw_berlin.tableplanner.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository repo;

    public List<Customer> getAll() {
        return repo.findAll();
    }

    public Customer getById(Long id) {
        return repo.findById(id).orElseThrow(RuntimeException::new);
    }

    public Customer save(Customer customer) {
        return repo.save(customer);
    }

    public Customer update(Long id, Customer updatedCustomer) {
        Customer existing = repo.findById(id).orElseThrow(RuntimeException::new);
        existing.setFirstName(updatedCustomer.getFirstName());
        existing.setLastName(updatedCustomer.getLastName());
        existing.setPhoneNumber(updatedCustomer.getPhoneNumber());
        return repo.save(existing);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}