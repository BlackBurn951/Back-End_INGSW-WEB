package com.example.progettowebtest.DAO.Transazioni;

import com.example.progettowebtest.Model.Proxy.Transazione;
import com.example.progettowebtest.Model.Transazioni.Deposito;

import java.util.Vector;

public class DepositoDAOImpl implements DepositoDAO{
    @Override
    public Vector<Transazione> doRetriveAll() {
        return null;
    }

    @Override
    public Vector<Transazione> doRetriveAllForCC(String numCC) {
        return null;
    }

    @Override
    public Transazione doRetriveByKey(int id, boolean proxy) {
        return null;
    }

    @Override
    public boolean saveOrUpdate(Deposito depo) {
        return false;
    }

    @Override
    public boolean delete(Deposito depo) {
        return false;
    }
}
