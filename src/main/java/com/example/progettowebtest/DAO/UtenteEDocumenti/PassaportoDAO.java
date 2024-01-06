package com.example.progettowebtest.DAO.UtenteEDocumenti;

import com.example.progettowebtest.Model.Passaporto;

import java.util.Vector;

public interface PassaportoDAO {
    Vector<Passaporto> doRetrivelAll();
    Passaporto doRetriveByKey(String num_passaporto);
    boolean saveOrUpdate(Passaporto pp);
    boolean delete(Passaporto pp);
}
