package com.example.progettowebtest.DAO.Carte;

import com.example.progettowebtest.Model.Carte.CartaCredito;

import java.util.Vector;

public class CartaCreditoDAOImpl implements CartaCreditoDAO{
    private static CartaCreditoDAOImpl instance;

    private CartaCreditoDAOImpl() {}

    public static CartaCreditoDAOImpl getInstance() {
        if(instance==null)
            instance= new CartaCreditoDAOImpl();
        return instance;
    }

    @Override
    public Vector<CartaCredito> doRetriveAll() {
        return null;
    }

    @Override
    public CartaCredito doRetriveByKey(String numCarta) {
        return null;
    }

    @Override
    public boolean saveOrUpdate(CartaCredito cartaCred) {
        return false;
    }

    @Override
    public boolean delete(CartaCredito cartaCred) {
        return false;
    }
}
