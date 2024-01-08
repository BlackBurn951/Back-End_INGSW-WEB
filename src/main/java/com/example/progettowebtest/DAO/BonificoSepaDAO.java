package com.example.progettowebtest.DAO;


import com.example.progettowebtest.Model.BonificoSepa;

import java.util.Vector;

public interface BonificoSepaDAO {
    Vector<BonificoSepa> doRetriveAll();
    BonificoSepa doRetriveByKey(int id);
    boolean saveOrUpdate(BonificoSepa bonSepa);
    boolean delete(BonificoSepa bonSepa);
}
