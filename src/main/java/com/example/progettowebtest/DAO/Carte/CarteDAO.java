package com.example.progettowebtest.DAO.Carte;

import com.example.progettowebtest.Model.Carte.Carte;
import com.example.progettowebtest.Model.Carte.TipiCarte;

import java.util.Vector;

public interface CarteDAO {
    Vector<Carte> doRetriveAll();

    Vector<Carte> doRetriveAllForCC(String numCC);

    Carte doRetriveByKey(String numCarta, TipiCarte tipo, boolean proxy);

    boolean saveOrUpdate(Carte carta, TipiCarte tipo);

    boolean delete(Carte carta);

}
