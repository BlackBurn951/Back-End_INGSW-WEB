package com.example.progettowebtest.DAO.Utente_Documenti;

import com.example.progettowebtest.Model.Utente_Documenti.Passaporto;

import java.util.Vector;

public interface PassaportoDAO {
    Vector<Passaporto> doRetriveAll();
    Passaporto doRetriveByKey(String numIdentificativo);
    boolean saveOrUpdate(Passaporto passa);
    boolean delete(Passaporto passa);
}
