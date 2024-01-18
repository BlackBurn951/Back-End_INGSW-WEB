package com.example.progettowebtest.DAO.Transazioni;


import com.example.progettowebtest.Model.Transazioni.BonificoSepa;

import java.util.Vector;

public interface BonificoSepaDAO {
    Vector<BonificoSepa> doRetriveAll();
    BonificoSepa doRetriveByKey(int id, boolean proxy);
    boolean saveOrUpdate(BonificoSepa bonSepa);
    boolean delete(BonificoSepa bonSepa);
}
