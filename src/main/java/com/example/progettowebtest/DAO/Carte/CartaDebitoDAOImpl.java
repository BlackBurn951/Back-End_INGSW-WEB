package com.example.progettowebtest.DAO.Carte;

import com.example.progettowebtest.Model.Carte.CartaDebito;

import java.util.Vector;

public class CartaDebitoDAOImpl implements CartaDebitoDAO {
    private static CartaDebitoDAOImpl instance;

    private CartaDebitoDAOImpl() {}

    public static CartaDebitoDAOImpl getInstance() {
        if(instance==null)
            instance= new CartaDebitoDAOImpl();
        return instance;
    }

    @Override
    public Vector<CartaDebito> doRetriveAll() {
        return null;
    }

    @Override
    public CartaDebito doRetriveByKey(String numCarta) {
        return null;
    }

    @Override
    public boolean saveOrUpdate(CartaDebito cartaDeb) {
        return false;
    }

    @Override
    public boolean delete(CartaDebito cartaDeb) {
        return false;
    }
}
