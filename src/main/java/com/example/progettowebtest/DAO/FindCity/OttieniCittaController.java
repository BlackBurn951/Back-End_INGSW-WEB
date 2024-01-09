package com.example.progettowebtest.DAO.FindCity;

import com.example.progettowebtest.DAO.Indirizzo.DatiComuneDAOImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class OttieniCittaController {

    @GetMapping("/cities")
    public List<String> getCitiesSuggestions(@RequestParam("query") String query) {
        List<String> cities = DatiComuneDAOImpl.getInstance().restituisciCitta(query);

        return cities;
    }
}
