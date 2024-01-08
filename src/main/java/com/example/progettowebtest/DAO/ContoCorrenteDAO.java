package com.example.progettowebtest.DAO;

import com.example.progettowebtest.Model.ContoCorrente;

import java.util.Vector;

public interface ContoCorrenteDAO {
    Vector<ContoCorrente> doRetriveAll();
    ContoCorrente doRetriveByKey(int numConto);
    boolean saveOrUpdate(ContoCorrente contoCorr);
    boolean delete(ContoCorrente contoCorr);


}
