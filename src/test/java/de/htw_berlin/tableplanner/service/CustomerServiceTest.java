package de.htw_berlin.tableplanner.service;

import de.htw_berlin.tableplanner.model.Customer;
import de.htw_berlin.tableplanner.model.Restaurant;
import de.htw_berlin.tableplanner.repository.CustomerRepository;
import de.htw_berlin.tableplanner.security.CurrentRestaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class CustomerServiceTest {

    @Autowired
    private CustomerService service;

    @MockitoBean
    private CustomerRepository customerRepo;

    @MockitoBean
    private CurrentRestaurant currentRestaurant;

    @BeforeEach
    void setUp() {
        when(currentRestaurant.getId()).thenReturn(1L);
    }

    @Test
    @DisplayName("update() should update fields when customer exists")
    void updateCustomer_withValidId() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);

        Customer existingCustomer = new Customer();
        existingCustomer.setId(1L);
        existingCustomer.setRestaurant(restaurant);

        Customer updatedData = new Customer();
        updatedData.setFirstName("Nando");
        updatedData.setLastName("Patton");
        updatedData.setPhoneNumber("0123456789");

        when(customerRepo.findById(1L)).thenReturn(Optional.of(existingCustomer));
        when(customerRepo.save(existingCustomer)).thenReturn(existingCustomer);

        Customer result = service.update(1L, updatedData);

        assertEquals("Nando", result.getFirstName());
        assertEquals("Patton", result.getLastName());
        assertEquals("0123456789", result.getPhoneNumber());
    }

    @Test
    @DisplayName("update() should throw a RuntimeException when customer is not found")
    void updateCustomer_withNonExistingId() {
        Customer updatedData = new Customer();

        assertThrows(RuntimeException.class, () -> service.update(1L, updatedData));
    }
}