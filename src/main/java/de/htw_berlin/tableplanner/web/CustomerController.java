package de.htw_berlin.tableplanner.web;

import lombok.AllArgsConstructor;
import de.htw_berlin.tableplanner.model.Customer;
import de.htw_berlin.tableplanner.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class CustomerController {
    private final CustomerService service;

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return service.getAll();
    }

    @GetMapping("/customers/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/customers")
    public Customer createCustomer(@RequestBody Customer customer) {
        return service.save(customer);
    }

    @PutMapping("/customers/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        return service.update(id, customer);
    }

    @DeleteMapping("/customers/{id}")
    public void deleteCustomerById(@PathVariable Long id) {
        service.deleteById(id);
    }
}