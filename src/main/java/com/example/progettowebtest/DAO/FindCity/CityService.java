package com.example.progettowebtest.DAO.FindCity;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<String> findCitiesByQuery(String query) {
        // Implementa la logica per trovare le città nel database che corrispondono alla query
        return cityRepository.findCityNamesByPartialName(query);
    }
}
