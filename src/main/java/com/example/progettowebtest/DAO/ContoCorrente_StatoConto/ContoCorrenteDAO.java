package com.example.progettowebtest.DAO.ContoCorrente_StatoConto;

import com.example.progettowebtest.Model.ContoCorrente.ContoCorrente;

import java.util.Vector;

public interface ContoCorrenteDAO {
    Vector<ContoCorrente> doRetriveAll();
    ContoCorrente doRetriveByKey(String numConto);
    ContoCorrente doRetriveByAttribute(String attributo);
    boolean saveOrUpdate(ContoCorrente contoCorr, boolean fristTime);
    boolean delete(String numCC);
    void setPinChiaro(String pinChiaro);
    String getPinChiaro();
}
