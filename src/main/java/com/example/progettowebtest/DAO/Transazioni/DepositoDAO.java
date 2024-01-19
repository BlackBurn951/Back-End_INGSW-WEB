package com.example.progettowebtest.DAO.Transazioni;

import com.example.progettowebtest.Model.Proxy.Transazione;
import com.example.progettowebtest.Model.Transazioni.Deposito;

import java.util.Vector;

public interface DepositoDAO {
    Vector<Transazione> doRetriveAll();
    Vector<Transazione> doRetriveAllForCC(String numCC);
    Transazione doRetriveByKey(int id, boolean proxy);
    boolean saveOrUpdate(Deposito depo, String numCC);
    boolean delete(Transazione depo);
}
