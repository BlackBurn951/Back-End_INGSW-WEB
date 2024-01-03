package com.example.progettowebtest.DAO;

import com.example.progettowebtest.Model.Utente;

import java.util.Objects;
import java.util.Vector;

public interface UtenteDAO {
    Vector<Utente> doRetriveAll();
    Utente doRetriveByKey(String cf);
    void saveOrUpdate(Utente ut);
    void delete(Utente ut);
}
