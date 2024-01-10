package com.example.progettowebtest.DAO.Utente_Documenti;


import com.example.progettowebtest.Model.Patente;

import java.util.Vector;

public interface PatenteDAO {
    Vector<Patente> doRetriveAll();
    Patente doRetriveByKey(String numIdentificativo);
    boolean saveOrUpdate(Patente pat);
    boolean delete(Patente pat);
}
