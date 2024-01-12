package com.example.progettowebtest.DAO.ContoCorrente_StatoConto;

import com.example.progettowebtest.Model.ContoCorrente.Salvadanaio;

import java.util.Vector;

public interface SalvadanaioDAO {
    Vector<Salvadanaio> doRetriveAll();
    Salvadanaio doRetriveByKey(int id);
    boolean saveOrUpdate(Salvadanaio salva);
    boolean delete(Salvadanaio salva);
}