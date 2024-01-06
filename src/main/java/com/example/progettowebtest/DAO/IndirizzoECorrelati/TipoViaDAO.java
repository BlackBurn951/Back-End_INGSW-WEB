package com.example.progettowebtest.DAO.IndirizzoECorrelati;

import com.example.progettowebtest.Model.TipoVia;

import java.util.Vector;

public interface TipoViaDAO {
    Vector<TipoVia> doRetriveAll();
    TipoVia doRetriveByKey(int idVia);
    TipoVia doRetriveByAttribute(String tipo);
    boolean saveOrUpdate(TipoVia tipo);
    boolean delete(TipoVia tipo);
}
