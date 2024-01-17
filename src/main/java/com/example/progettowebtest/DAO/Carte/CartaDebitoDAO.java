package com.example.progettowebtest.DAO.Carte;

import com.example.progettowebtest.Model.Carte.CartaDebito;

import java.util.Vector;

public interface CartaDebitoDAO {
    Vector<CartaDebito> doRetriveAll();
    CartaDebito doRetriveByKey(String numCarta);
    boolean saveOrUpdate(CartaDebito cartaDeb);
    boolean delete(CartaDebito cartaDeb);
}
