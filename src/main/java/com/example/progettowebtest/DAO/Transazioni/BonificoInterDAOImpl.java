package com.example.progettowebtest.DAO.Transazioni;

import com.example.progettowebtest.Model.Proxy.Transazione;
import com.example.progettowebtest.Model.Transazioni.BonificoInter;

import java.util.Vector;

public class BonificoInterDAOImpl implements BonificoInterDAO{
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
    public boolean saveOrUpdate(BonificoInter bonInt) {
        return false;
    }

    @Override
    public boolean delete(BonificoInter bonInt) {
        return false;
    }
}
