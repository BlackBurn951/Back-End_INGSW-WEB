package com.example.progettowebtest.DAO.Transazioni;

import com.example.progettowebtest.Model.Transazioni.Prelievo;

import java.util.Vector;

public interface PrelievoDAO {
    Vector<Prelievo> doRetriveAll();
    Prelievo doRetriveByKey(int id, boolean proxy);
    boolean saveOrUpdate(Prelievo prel);
    boolean delete(Prelievo prel);
}
