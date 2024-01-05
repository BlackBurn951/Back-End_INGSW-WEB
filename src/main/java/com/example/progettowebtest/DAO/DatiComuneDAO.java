package com.example.progettowebtest.DAO;

import com.example.progettowebtest.Model.ColonneDatiComune;
import com.example.progettowebtest.Model.DatiComune;
import com.example.progettowebtest.Model.Indirizzo;

import java.util.Vector;

public interface DatiComuneDAO {
    Vector<DatiComune> doRetriveAll();
    DatiComune doRetriveByKey(int idComune);
    DatiComune doRetriveByAttribute(String att, ColonneDatiComune val);
    boolean saveOrUpdate(DatiComune comune);
    boolean delete(DatiComune comune);
}
