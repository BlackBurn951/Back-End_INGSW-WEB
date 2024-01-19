package com.example.progettowebtest.DAO.Carte;

import com.example.progettowebtest.Model.Carte.Carte;

import java.util.Vector;

public interface CarteDAO {
    Vector<Carte> doRetriveAll();
    Vector<Carte> doRetriveAllForCC(String numCC);
    Carte doRetriveByKey(String numCarta, boolean tipo, boolean proxy);
    boolean saveOrUpdate(Carte carta);
    boolean delete(Carte carta, boolean tipo);

}
