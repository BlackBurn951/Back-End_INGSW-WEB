package com.example.progettowebtest.DAO.Transazioni;

import com.example.progettowebtest.Model.Transazioni.Deposito;

import java.util.Vector;

public interface DepositoDAO {
    Vector<Deposito> doRetriveAll();
    Deposito doRetriveByKey(int id, boolean proxy);
    boolean saveOrUpdate(Deposito depo);
    boolean delete(Deposito depo);
}
