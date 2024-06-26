package com.example.progettowebtest.DAO.ContoCorrente_StatoConto;

import com.example.progettowebtest.Model.ContoCorrente.RelStatoConto;
import com.example.progettowebtest.Model.Stato;

import java.util.Vector;

public interface RelStatoContoDAO {
    Vector<RelStatoConto> doRetriveAll();
    RelStatoConto doRetriveByKey(int id);
    Vector<RelStatoConto> doRetriveByAttribute(String numCC);
    Stato doRetriveActualState(String numCC);
    RelStatoConto doRetriveActualRel(String numCC);
    boolean saveOrUpdate(RelStatoConto rel);
    boolean delete(String numCC);
}
