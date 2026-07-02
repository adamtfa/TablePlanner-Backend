package de.htw_berlin.tableplanner.service;

import de.htw_berlin.tableplanner.model.RestaurantTable;
import de.htw_berlin.tableplanner.repository.RestaurantTableRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class TableServiceTest {

    @Autowired
    private TableService service;

    @MockitoBean
    private RestaurantTableRepository tableRepo;

    @Test
    @DisplayName("update() should update fields when table exists")
    void updateTable_withValidId() {
        RestaurantTable existingTable = new RestaurantTable();
        existingTable.setId(1L);

        RestaurantTable updatedData = new RestaurantTable();
        updatedData.setTableNumber(9);
        updatedData.setCapacity(6);
        updatedData.setAvailable(true);

        when(tableRepo.findById(1L)).thenReturn(Optional.of(existingTable));
        when(tableRepo.save(existingTable)).thenReturn(existingTable);

        RestaurantTable result = service.update(1L, updatedData);

        assertEquals(9, result.getTableNumber());
        assertEquals(6, result.getCapacity());
        assertTrue(result.isAvailable());
    }

    @Test
    @DisplayName("update() should throw a RuntimeException when table is not found")
    void updateTable_withNonExistingId() {
        RestaurantTable updatedData = new RestaurantTable();

        assertThrows(RuntimeException.class, () -> service.update(1L, updatedData));
    }
}