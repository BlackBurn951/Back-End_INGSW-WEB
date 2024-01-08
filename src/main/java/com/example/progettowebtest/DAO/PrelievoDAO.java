package com.example.progettowebtest.DAO;

import com.example.progettowebtest.Model.Prelievo;

import java.util.Vector;

public interface PrelievoDAO {
    Vector<Prelievo> doRetriveAll();
    Prelievo doRetriveByKey(int id);
    boolean saveOrUpdate(Prelievo prel);
    boolean delete(Prelievo prel);
}
