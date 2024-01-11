package com.example.progettowebtest.DAO;

import com.example.progettowebtest.Model.ContoCorrente.Salvadanaio;
import com.example.progettowebtest.Model.Stato;
import com.example.progettowebtest.Model.TabelleCorelateStato;

import java.util.Vector;

public interface StatoDAO {
    Vector<Stato> doRetriveAll();
    Stato doRetriveByKey(int id);
    boolean saveOrUpdate(Stato st);
    boolean delete(Stato st);
}
