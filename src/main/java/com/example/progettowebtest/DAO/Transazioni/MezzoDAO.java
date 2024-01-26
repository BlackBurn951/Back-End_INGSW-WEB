package com.example.progettowebtest.DAO.Transazioni;

import com.example.progettowebtest.Model.Transazioni.BonificoInter;
import com.example.progettowebtest.Model.Transazioni.Mezzo;

import java.util.Vector;

public interface MezzoDAO {
    Vector<Mezzo> doRetriveAll();
    Mezzo doRetriveByKey(int id);
    boolean saveOrUpdate(BonificoInter bonInt, String numCC);
    boolean delete(BonificoInter bonInt);
}
