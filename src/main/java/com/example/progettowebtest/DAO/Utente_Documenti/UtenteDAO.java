package com.example.progettowebtest.DAO.Utente_Documenti;

import com.example.progettowebtest.ClassiRequest.IdentificativiUtente;
import com.example.progettowebtest.Model.Utente_Documenti.Utente;

import java.util.Vector;

public interface UtenteDAO {
    Vector<Utente> doRetriveAll();
    Utente doRetriveByKey(String id, IdentificativiUtente col);
    boolean saveOrUpdate(Utente ut);
    boolean delete(Utente ut);
}
