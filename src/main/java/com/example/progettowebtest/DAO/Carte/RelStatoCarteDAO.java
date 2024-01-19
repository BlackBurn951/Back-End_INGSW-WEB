package com.example.progettowebtest.DAO.Carte;

import com.example.progettowebtest.Model.Carte.RelStatoCarta;
import com.example.progettowebtest.Model.Stato;

import java.util.Vector;

public interface RelStatoCarteDAO {
    Vector<RelStatoCarta> doRetriveAll();
    RelStatoCarta doRetriveByKey(int id);
    Vector<RelStatoCarta> doRetriveByAttribute(String numCarta, boolean tipo);
    Stato doRetriveActualState(String numCarta, boolean tipo);
    boolean saveOrUpdate(RelStatoCarta rel, boolean tipo);
    boolean delete(RelStatoCarta rel);
}
