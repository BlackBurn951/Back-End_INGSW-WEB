package com.example.progettowebtest.DAO.Transazioni;


import com.example.progettowebtest.Model.Proxy.Transazione;
import com.example.progettowebtest.Model.Transazioni.Bollettino;

import java.util.Vector;

public interface BollettinoDAO{
    Vector<Transazione> doRetriveAll();
    Vector<Transazione> doRetriveAllForCC(String numCC);
    int retriveLastId();
    Transazione doRetriveByKey(int id, boolean proxy);
    boolean saveOrUpdate(Bollettino bol, String numCC);
    boolean delete(Transazione bol);
}
