package com.example.progettowebtest.DAO;

import com.example.progettowebtest.Model.Indirizzo;
import com.example.progettowebtest.Model.Utente;

import java.util.Vector;

public interface IndirizzoDAO {
    Vector<Indirizzo> doRetriveAll();
    Indirizzo doRetriveByKey(String nomeVia, String numCivico, String comune, String tipologia);
    boolean saveOrUpdate(Indirizzo ind);
    boolean delete(Indirizzo ind);
}