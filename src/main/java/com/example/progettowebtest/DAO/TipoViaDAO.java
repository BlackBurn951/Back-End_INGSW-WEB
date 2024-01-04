package com.example.progettowebtest.DAO;

import com.example.progettowebtest.Model.Indirizzo;
import com.example.progettowebtest.Model.TipoVia;

import java.util.Vector;

public interface TipoViaDAO {
    Vector<TipoVia> doRetriveAll();
    TipoVia doRetriveByKey(int idVia);
    boolean saveOrUpdate(TipoVia tipo);
    boolean delete(TipoVia tipo);
}
