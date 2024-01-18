package com.example.progettowebtest.DAO.Transazioni;


import com.example.progettowebtest.Model.Proxy.Transazione;
import com.example.progettowebtest.Model.Transazioni.BonificoSepa;

import java.util.Vector;

public interface BonificoSepaDAO {
    Vector<Transazione> doRetriveAll();
    Vector<Transazione> doRetriveAllForCC(String numCC);
    Transazione doRetriveByKey(int id, boolean proxy);
    boolean saveOrUpdate(BonificoSepa bonSepa, String numCC);
    boolean delete(BonificoSepa bonSepa);
}
