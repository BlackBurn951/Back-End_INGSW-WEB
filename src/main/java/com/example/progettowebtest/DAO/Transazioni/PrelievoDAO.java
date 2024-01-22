package com.example.progettowebtest.DAO.Transazioni;

import com.example.progettowebtest.Model.Proxy.Transazione;
import com.example.progettowebtest.Model.Transazioni.Prelievo;

import java.util.Vector;

public interface PrelievoDAO {
    Vector<Transazione> doRetriveAll();
    Vector<Transazione> doRetriveAllForCC(String numCC);
    int retriveLastId();

    Transazione doRetriveByKey(int id, boolean proxy);
    boolean saveOrUpdate(Prelievo prel, String numCC);
    boolean delete(int id);
}
