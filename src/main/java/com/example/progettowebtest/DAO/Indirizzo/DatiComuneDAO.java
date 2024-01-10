package com.example.progettowebtest.DAO.Indirizzo;

import ColonneDatiComune;
import DatiComune;

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
