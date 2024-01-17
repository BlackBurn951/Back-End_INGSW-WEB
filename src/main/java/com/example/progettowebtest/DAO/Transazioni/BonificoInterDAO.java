package com.example.progettowebtest.DAO.Transazioni;


import com.example.progettowebtest.Model.Transazioni.BonificoInter;

import java.util.Vector;

public interface BonificoInterDAO {
    Vector<BonificoInter> doRetriveAll();
    BonificoInter doRetriveByKey(int id);
    boolean saveOrUpdate(BonificoInter bonInt);
    boolean delete(BonificoInter bonInt);
}
