package de.htw_berlin.tableplanner.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RestaurantTable {

    private int tableNumber;
    private int capacity;
    private boolean isAvailable;
}
