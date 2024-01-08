package com.example.progettowebtest.DAO;


import com.example.progettowebtest.Model.Bollettino;

import java.util.Vector;

public interface BollettinoDAO {
    Vector<Bollettino> doRetriveAll();
    Bollettino doRetriveByKey(int id);
    boolean saveOrUpdate(Bollettino bol);
    boolean delete(Bollettino bol);
}
