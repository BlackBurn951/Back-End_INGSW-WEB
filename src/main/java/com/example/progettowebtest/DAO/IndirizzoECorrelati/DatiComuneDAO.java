package com.example.progettowebtest.DAO.IndirizzoECorrelati;

import com.example.progettowebtest.Model.ColonneDatiComune;
import com.example.progettowebtest.Model.DatiComune;

import java.util.Vector;

public interface DatiComuneDAO {
    Vector<DatiComune> doRetriveAll();
    DatiComune doRetriveByKey(int idComune);
    DatiComune doRetriveByAttribute(String att, ColonneDatiComune val);
    boolean saveOrUpdate(DatiComune comune);
    boolean delete(DatiComune comune);
}
