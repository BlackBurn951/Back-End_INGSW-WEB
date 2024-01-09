package com.example.progettowebtest.DAO.FindCity;

import com.example.progettowebtest.DAO.Indirizzo.DatiComuneDAOImpl;
import com.example.progettowebtest.Model.ColonneDatiComune;
import com.example.progettowebtest.Model.DatiComune;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Vector;

@Service
public class CityService {


    public List<String> findCitiesByQuery(String query) {

        return DatiComuneDAOImpl.getInstance().restituisciCitta(query);
    }
}
