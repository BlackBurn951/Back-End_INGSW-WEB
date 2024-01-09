package com.example.progettowebtest.DAO.Indirizzo;

import com.example.progettowebtest.Model.ColonneDatiComune;
import com.example.progettowebtest.Model.DatiComune;
import com.example.progettowebtest.Model.Indirizzo;

import java.util.List;
import java.util.Vector;

public interface DatiComuneDAO {
    Vector<DatiComune> doRetriveAll();
    DatiComune doRetriveByKey(int idComune);
    Vector<DatiComune> doRetriveByAttribute(String att, ColonneDatiComune val);
    List<String> restituisciCitta(String citta);
    boolean saveOrUpdate(DatiComune comune);
    boolean delete(DatiComune comune);
}
