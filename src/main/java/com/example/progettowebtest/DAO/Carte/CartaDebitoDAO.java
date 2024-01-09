package com.example.progettowebtest.DAO.Carte;

import com.example.progettowebtest.Model.CartaDebito;

import java.util.Vector;

public interface CartaDebitoDAO {
    Vector<CartaDebito> doRetriveAll();
    CartaDebito doRetriveByKey(int numCarta);
    boolean saveOrUpdate(CartaDebito cartaDeb);
    boolean delete(CartaDebito cartaDeb);
}
