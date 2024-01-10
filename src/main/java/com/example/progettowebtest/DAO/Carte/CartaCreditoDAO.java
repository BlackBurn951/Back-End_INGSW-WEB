package com.example.progettowebtest.DAO.Carte;


import com.example.progettowebtest.Model.Carte.CartaCredito;

import java.util.Vector;

public interface CartaCreditoDAO {

    Vector<CartaCredito> doRetriveAll();
    CartaCredito doRetriveByKey(int numCarta);
    boolean saveOrUpdate(CartaCredito cartaCred);
    boolean delete(CartaCredito cartaCred);

}
