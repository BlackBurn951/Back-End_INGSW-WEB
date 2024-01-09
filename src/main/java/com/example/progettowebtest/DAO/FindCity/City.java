package com.example.progettowebtest.DAO.FindCity;

import jakarta.persistence.*;

@Entity
@Table(name = "cities")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Altri attributi come CAP, provincia, regione, etc. possono essere aggiunti qui

    // Costruttori, getter e setter, e altri metodi

    public City() {
        // Costruttore vuoto per JPA
    }

    public City(String name) {
        this.name = name;
    }

    // Getters e setters per id e name
    // ... altri metodi accessori

}
