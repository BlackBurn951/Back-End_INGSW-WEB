package com.example.progettowebtest.DAO.Carte;

import com.example.progettowebtest.Model.Carte.RelStatoCarta;
import com.example.progettowebtest.Model.Carte.TipiCarte;
import com.example.progettowebtest.Model.Stato;

import java.util.Vector;

public interface RelStatoCarteDAO {
    Vector<RelStatoCarta> doRetriveAll();
    RelStatoCarta doRetriveByKey(int id);
    Vector<RelStatoCarta> doRetriveByAttribute(String numCarta, TipiCarte tipo);
    RelStatoCarta doRetriveActualRel(String numCarta, TipiCarte tipo, Stato st);
    Stato doRetriveActualState(String numCarta, TipiCarte tipo);
    boolean saveOrUpdate(RelStatoCarta rel, TipiCarte tipo);
    boolean delete(String numcarta, TipiCarte tipo);
}
