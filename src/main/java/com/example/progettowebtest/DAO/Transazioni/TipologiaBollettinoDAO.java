package com.example.progettowebtest.DAO.Transazioni;

import com.example.progettowebtest.Model.Transazioni.Bollettino;
import com.example.progettowebtest.Model.Transazioni.TipologiaBollettino;

import java.util.Vector;

public interface TipologiaBollettinoDAO {
    Vector<TipologiaBollettino> doRetriveAll();
    TipologiaBollettino doRetriveByKey(int id);
    boolean saveOrUpdate(TipologiaBollettino bol);
    boolean delete(TipologiaBollettino bol);
}
