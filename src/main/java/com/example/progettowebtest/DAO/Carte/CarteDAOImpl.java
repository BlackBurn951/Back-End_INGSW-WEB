package com.example.progettowebtest.DAO.Carte;

import com.example.progettowebtest.Connection.DbConn;
import com.example.progettowebtest.DAO.MagnusDAO;
import com.example.progettowebtest.Model.Carte.*;
import com.example.progettowebtest.Model.Proxy.TipoTransazione;
import com.example.progettowebtest.Model.Proxy.Transazione;
import com.example.progettowebtest.Model.Proxy.TransazioneProxy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class CarteDAOImpl implements CarteDAO{
    @Override
    public Vector<Carte> doRetriveAll() {
        return null;
    }

    @Override
    public Vector<Carte> doRetriveAllForCC(String numCC) {
        Vector<Carte> result= new Vector<>();

        prendiCarteCredito(result, numCC);
        prendicarteDebito(result, numCC);

        return result;
    }

    @Override
    public Carte doRetriveByKey(String numCarta, boolean tipo, boolean proxy) {

        return null;
    }

    @Override
    public boolean saveOrUpdate(Carte carta) {
        return false;
    }

    @Override
    public boolean delete(Carte carta) {
        return false;
    }


    //Metodi di servizio
    private void prendiCarteCredito(Vector<Carte> result, String numCC) {
        String query= "select * from carta_di_credito where num_cc= ?";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            statement.setString(1, numCC);

            ResultSet queryResult= statement.executeQuery();

            while(queryResult.next()) {
                result.add(new CartaProxy(queryResult.getString("num_carta_credito"), queryResult.getBoolean("stato_pagamento_online"), queryResult.getDate("data_creazione").toString(),
                        queryResult.getDate("data_scadenza").toString(), queryResult.getString("cvv"), queryResult.getBoolean("carta_fisica"), queryResult.getInt("canone_mensile"),
                        queryResult.getString("pin"), MagnusDAO.getInstance().getRelStatoCarteDAO().doRetriveActualState(queryResult.getString("num_carta_credito"), true), TipiCarte.CREDITO));
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void prendicarteDebito(Vector<Carte> result, String numCC) {
        String query= "select * from carta_di_debito where num_cc= ?";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            statement.setString(1, numCC);

            ResultSet queryResult= statement.executeQuery();

            while(queryResult.next()) {
                result.add(new CartaProxy(queryResult.getString("num_carta_credito"), queryResult.getBoolean("stato_pagamento_online"), queryResult.getDate("data_creazione").toString(),
                        queryResult.getDate("data_scadenza").toString(), queryResult.getString("cvv"), queryResult.getBoolean("carta_fisica"), queryResult.getInt("canone_mensile"),
                        queryResult.getString("pin"), MagnusDAO.getInstance().getRelStatoCarteDAO().doRetriveActualState(queryResult.getString("num_carta_credito"), true), TipiCarte.CREDITO));
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
