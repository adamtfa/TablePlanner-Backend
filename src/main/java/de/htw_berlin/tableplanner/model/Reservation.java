package de.htw_berlin.tableplanner.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reservation {

    private LocalDate date;
    private LocalTime time;
    private int numberOfGuests;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private RestaurantTable table;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}