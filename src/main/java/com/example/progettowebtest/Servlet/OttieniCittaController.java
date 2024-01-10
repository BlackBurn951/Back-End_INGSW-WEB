package com.example.progettowebtest.Servlet;

import com.example.progettowebtest.DAO.Indirizzo.DatiComuneDAOImpl;
import com.example.progettowebtest.Model.ColonneDatiComune;
import com.example.progettowebtest.Model.DatiComune;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Vector;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class OttieniCittaController {

    @GetMapping("/cities")
    public List<String> getCitiesSuggestions(@RequestParam("query") String query) {
        List<String> cities = DatiComuneDAOImpl.getInstance().restituisciCitta(query);
        return cities;
    }

    @GetMapping("/datiCitta")
    public List<String> getDatiCitta(@RequestParam("query") String query) {
        Vector<DatiComune> datiCitta= DatiComuneDAOImpl.getInstance().doRetriveByAttribute(query, ColonneDatiComune.NOME_COMUNE);
        Vector<String> result= new Vector<>();

        result.add(datiCitta.get(0).getCap());
        result.add(datiCitta.get(0).getProvincia());
        result.add(datiCitta.get(0).getRegione());

        return result;
    }
}
