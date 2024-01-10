package com.example.progettowebtest.DAO.Indirizzo;

import com.example.progettowebtest.Model.Indirizzo.Indirizzo;

import java.util.Vector;

public interface IndirizzoDAO {
    Vector<Indirizzo> doRetriveAll();
    Indirizzo doRetriveByKey(String nomeVia, String numCivico, int comune, int tipologia);
    boolean saveOrUpdate(Indirizzo ind);
    boolean delete(Indirizzo ind);
}
