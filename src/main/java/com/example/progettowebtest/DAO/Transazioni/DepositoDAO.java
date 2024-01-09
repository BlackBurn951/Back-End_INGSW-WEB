package com.example.progettowebtest.DAO.Transazioni;

import com.example.progettowebtest.Model.Deposito;

import java.util.Vector;

public interface DepositoDAO {
    Vector<Deposito> doRetriveAll();
    Deposito doRetriveByKey(int id);
    boolean saveOrUpdate(Deposito depo);
    boolean delete(Deposito depo);
}
