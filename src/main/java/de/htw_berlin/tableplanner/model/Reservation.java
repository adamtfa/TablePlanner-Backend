package de.htw_berlin.tableplanner.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
public class Reservation {

    private LocalDate date;
    private LocalTime time;
    private int numberOfGuests;
    private Customer customer;
    private RestaurantTable table;
    
}