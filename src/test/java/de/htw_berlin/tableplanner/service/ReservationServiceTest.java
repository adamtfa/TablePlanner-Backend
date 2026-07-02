package de.htw_berlin.tableplanner.service;

import de.htw_berlin.tableplanner.model.Customer;
import de.htw_berlin.tableplanner.model.Reservation;
import de.htw_berlin.tableplanner.model.RestaurantTable;
import de.htw_berlin.tableplanner.repository.CustomerRepository;
import de.htw_berlin.tableplanner.repository.ReservationRepository;
import de.htw_berlin.tableplanner.repository.RestaurantTableRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class ReservationServiceTest {

    @Autowired
    private ReservationService service;

    @MockitoBean
    private ReservationRepository reservationRepo;
    @MockitoBean
    private CustomerRepository customerRepo;
    @MockitoBean
    private RestaurantTableRepository tableRepo;

    @Test
    @DisplayName("should set customer and table when both IDs are valid")
    void saveReservation_withValidIds() {
        Customer testCustomer = new Customer();
        testCustomer.setId(1L);
        testCustomer.setFirstName("Adam");

        RestaurantTable testTable = new RestaurantTable();
        testTable.setId(1L);
        testTable.setTableNumber(7);

        Reservation newReservation = new Reservation();
        newReservation.setDate(LocalDate.of(2026, 7, 2));
        newReservation.setTime(LocalTime.of(20, 0));
        newReservation.setNumberOfGuests(4);

        when(customerRepo.findById(1L)).thenReturn(Optional.of(testCustomer));
        when(tableRepo.findById(1L)).thenReturn(Optional.of(testTable));
        when(reservationRepo.save(newReservation)).thenReturn(newReservation);

        Reservation result = service.save(newReservation, 1L, 1L);

        assertEquals(testCustomer, result.getCustomer());
        assertEquals(testTable, result.getTable());
    }

    @Test
    @DisplayName("should throw a RuntimeException when customer is not found")
    void saveReservation_withInvalidCustomer() {
        Reservation newReservation = new Reservation();

        assertThrows(RuntimeException.class, () -> service.save(newReservation, 1L, 1L));
    }

    @Test
    @DisplayName("should update fields and set customer and table when update succeeds")
    void updateReservation_withValidIds() {
        Reservation existingReservation = new Reservation();
        existingReservation.setId(1L);

        Customer testCustomer = new Customer();
        testCustomer.setId(1L);
        testCustomer.setFirstName("Nando");

        RestaurantTable testTable = new RestaurantTable();
        testTable.setId(1L);
        testTable.setTableNumber(3);

        Reservation updatedData = new Reservation();
        updatedData.setDate(LocalDate.of(2026, 8, 15));
        updatedData.setTime(LocalTime.of(18, 30));
        updatedData.setNumberOfGuests(2);

        when(reservationRepo.findById(1L)).thenReturn(Optional.of(existingReservation));
        when(customerRepo.findById(1L)).thenReturn(Optional.of(testCustomer));
        when(tableRepo.findById(1L)).thenReturn(Optional.of(testTable));
        when(reservationRepo.save(existingReservation)).thenReturn(existingReservation);

        Reservation result = service.update(1L, updatedData, 1L, 1L);

        assertEquals(testCustomer, result.getCustomer());
        assertEquals(testTable, result.getTable());
        assertEquals(LocalDate.of(2026, 8, 15), result.getDate());
        assertEquals(LocalTime.of(18, 30), result.getTime());
        assertEquals(2, result.getNumberOfGuests());
    }

    @Test
    @DisplayName("should throw a RuntimeException when reservation is not found")
    void updateReservation_withNonExistingId() {
        Reservation updatedData = new Reservation();

        assertThrows(RuntimeException.class, () -> service.update(1L, updatedData, 1L, 1L));
    }
}