package com.example.progettowebtest.DAO.FindCity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    @Query("SELECT c.name FROM City c WHERE LOWER(c.name) LIKE LOWER(concat('%', :partialName, '%'))")
    List<String> findCityNamesByPartialName(@Param("partialName") String partialName);
}
