package com.example.progettowebtest.DAO;


import com.example.progettowebtest.Model.Patente;

import java.util.Vector;

public interface PatenteDAO {
    Vector<Patente> doRetriveAll();
    Patente doRetriveByKey(String numIdentificativo);
    boolean saveOrUpdate(Patente pat);
    boolean delete(Patente pat);
}
