package com.example.progettowebtest.DAO.Transazioni;


import com.example.progettowebtest.Model.Proxy.Transazione;
import com.example.progettowebtest.Model.Transazioni.BonificoInter;

import java.util.Vector;

public interface BonificoInterDAO {
    Vector<Transazione> doRetriveAll();
    Vector<Transazione> doRetriveAllForCC(String numCC);
    int retriveLastId();

    Transazione doRetriveByKey(int id, boolean proxy);
    boolean saveOrUpdate(BonificoInter bonInt, String numCC);
    boolean delete(int id);
}
