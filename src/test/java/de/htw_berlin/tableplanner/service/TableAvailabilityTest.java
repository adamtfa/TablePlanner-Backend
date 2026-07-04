package de.htw_berlin.tableplanner.service;

import de.htw_berlin.tableplanner.model.Reservation;
import de.htw_berlin.tableplanner.model.RestaurantTable;
import de.htw_berlin.tableplanner.repository.ReservationRepository;
import de.htw_berlin.tableplanner.repository.RestaurantTableRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class TableAvailabilityTest {

    private static final ZoneId RESTAURANT_ZONE = ZoneId.of("Europe/Berlin");

    @Autowired
    private TableService service;

    @MockitoBean
    private RestaurantTableRepository tableRepo;

    @MockitoBean
    private ReservationRepository reservationRepo;

    @Test
    @DisplayName("getById() should mark table unavailable during an active reservation")
    void getById_duringActiveReservation() {
        RestaurantTable table = new RestaurantTable();
        table.setId(1L);

        LocalDateTime start = LocalDateTime.now(RESTAURANT_ZONE).minusMinutes(30);
        Reservation reservation = new Reservation();
        reservation.setDate(start.toLocalDate());
        reservation.setTime(start.toLocalTime());

        when(tableRepo.findById(1L)).thenReturn(Optional.of(table));
        when(reservationRepo.findByTable_Id(1L)).thenReturn(List.of(reservation));

        RestaurantTable result = service.getById(1L);

        assertFalse(result.isAvailable());
    }

    @Test
    @DisplayName("getById() should mark table available after reservation window has passed")
    void getById_afterReservationEnded() {
        RestaurantTable table = new RestaurantTable();
        table.setId(1L);

        LocalDateTime start = LocalDateTime.now(RESTAURANT_ZONE).minusMinutes(200);
        Reservation reservation = new Reservation();
        reservation.setDate(start.toLocalDate());
        reservation.setTime(start.toLocalTime());

        when(tableRepo.findById(1L)).thenReturn(Optional.of(table));
        when(reservationRepo.findByTable_Id(1L)).thenReturn(List.of(reservation));

        RestaurantTable result = service.getById(1L);

        assertTrue(result.isAvailable());
    }

    @Test
    @DisplayName("getById() should mark table available before reservation starts")
    void getById_beforeReservationStarts() {
        RestaurantTable table = new RestaurantTable();
        table.setId(1L);

        LocalDateTime start = LocalDateTime.now(RESTAURANT_ZONE).plusMinutes(60);
        Reservation reservation = new Reservation();
        reservation.setDate(start.toLocalDate());
        reservation.setTime(start.toLocalTime());

        when(tableRepo.findById(1L)).thenReturn(Optional.of(table));
        when(reservationRepo.findByTable_Id(1L)).thenReturn(List.of(reservation));

        RestaurantTable result = service.getById(1L);

        assertTrue(result.isAvailable());
    }
}