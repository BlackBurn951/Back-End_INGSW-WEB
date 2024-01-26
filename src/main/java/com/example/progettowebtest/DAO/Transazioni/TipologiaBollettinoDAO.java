package com.example.progettowebtest.DAO.Transazioni;

import com.example.progettowebtest.Model.Proxy.Transazione;
import com.example.progettowebtest.Model.Transazioni.TipologiaBollettino;

import java.util.Vector;

public interface TipologiaBollettinoDAO {
    Vector<TipologiaBollettino> doRetriveAll();
    Vector<Transazione> doRetriveAllForCC(String numCC);
    TipologiaBollettino doRetriveByKey(int id);
    TipologiaBollettino doRetriveByAttribute(String tipo);
    boolean saveOrUpdate(TipologiaBollettino bol, String numCC);
    boolean delete(TipologiaBollettino bol);
}
