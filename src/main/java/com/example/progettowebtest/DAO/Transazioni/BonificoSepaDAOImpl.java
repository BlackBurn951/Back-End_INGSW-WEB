package com.example.progettowebtest.DAO.Transazioni;

import com.example.progettowebtest.Model.Proxy.Transazione;
import com.example.progettowebtest.Model.Transazioni.BonificoSepa;

import java.util.Vector;

public class BonificoSepaDAOImpl implements BonificoSepaDAO {
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
    public boolean saveOrUpdate(BonificoSepa bonSepa) {
        return false;
    }

    @Override
    public boolean delete(BonificoSepa bonSepa) {
        return false;
    }
}
