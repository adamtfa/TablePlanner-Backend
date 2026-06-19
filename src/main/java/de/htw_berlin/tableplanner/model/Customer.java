package de.htw_berlin.tableplanner.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {

    private String firstName;
    private String lastName;
    private String phoneNumber;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}