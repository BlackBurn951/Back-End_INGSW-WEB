package com.example.progettowebtest.DAO.Indirizzo;

import com.example.progettowebtest.Model.Indirizzo.TipoVia;

import java.util.Vector;

public interface TipoViaDAO {
    Vector<TipoVia> doRetriveAll();
    TipoVia doRetriveByKey(int idVia);
    TipoVia doRetriveByAttribute(String tipo);
    boolean saveOrUpdate(TipoVia tipo);
    boolean delete(TipoVia tipo);
}
