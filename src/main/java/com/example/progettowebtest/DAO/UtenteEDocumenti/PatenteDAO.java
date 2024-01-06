package com.example.progettowebtest.DAO.UtenteEDocumenti;

import com.example.progettowebtest.Model.Patente;

import java.util.Vector;

public interface PatenteDAO {
    Vector<Patente> doRetriveAll();
    Patente doRetriveByKey(String num_patente);
    boolean saveOrupdate(Patente pt);
    boolean delete(Patente pt);
}
