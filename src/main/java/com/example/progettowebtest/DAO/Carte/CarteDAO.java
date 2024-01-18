package com.example.progettowebtest.DAO.Carte;


import com.example.progettowebtest.Model.Carte.Carta;
import com.example.progettowebtest.Model.Carte.CartaCredito;

import java.util.Vector;

public interface CarteDAO {

    Vector<Carta> doRetriveAll();
    Vector<Carta> doRetriveAllForCC(String numCC);
    Carta doRetriveByKey(String numCarta, boolean tipo);
    boolean saveOrUpdate(CartaCredito cartaCred);
    boolean delete(CartaCredito cartaCred);

}
