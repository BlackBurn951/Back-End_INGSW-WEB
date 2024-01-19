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
    public Carte doRetriveByKey(String numCarta, TipiCarte tipo, boolean proxy) {
        Carte carta= null;
        String query= "";

        if(tipo==TipiCarte.CREDITO)
            query= "select * from carta_di_credito where num_carta_credito= ?";
        else
            query= "select * from carta_di_debito where num_carta_debito= ?";


        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            statement.setString(1, numCarta);

            ResultSet queryResult= statement.executeQuery();

            if(queryResult.next()) {
                if(tipo==TipiCarte.CREDITO) {
                    if(proxy)
                        carta= new CartaCredito(queryResult.getString("num_carta_credito"), queryResult.getBoolean("stato_pagamento_online"), queryResult.getDate("data_creazione").toString(),
                                queryResult.getDate("data_scadenza").toString(), queryResult.getString("cvv"), queryResult.getBoolean("carta_fisica"),
                                queryResult.getInt("canone_mensile"), queryResult.getString("pin"),
                                MagnusDAO.getInstance().getRelStatoCarteDAO().doRetriveActualState(queryResult.getString("num_carta_credito"), true),
                                MagnusDAO.getInstance().getContoCorrenteDAO().doRetriveByKey(queryResult.getString("num_cc")),
                                queryResult.getDouble("fido"));
                    else
                        carta= new CartaProxy(queryResult.getString("num_carta_credito"), queryResult.getBoolean("stato_pagamento_online"), queryResult.getDate("data_creazione").toString(),
                                queryResult.getDate("data_scadenza").toString(), queryResult.getString("cvv"), queryResult.getBoolean("carta_fisica"),
                                queryResult.getInt("canone_mensile"), queryResult.getString("pin"), queryResult.getDouble("fido"),
                                MagnusDAO.getInstance().getRelStatoCarteDAO().doRetriveActualState(queryResult.getString("num_carta_credito"), true), TipiCarte.CREDITO);
                }
                else {
                    if(proxy)
                        carta= new CartaDebito(queryResult.getString("num_carta_debito"), queryResult.getBoolean("stato_pagamento_online"), queryResult.getDate("data_creazione").toString(),
                                queryResult.getDate("data_scadenza").toString(), queryResult.getString("cvv"), queryResult.getBoolean("carta_fisica"),
                                queryResult.getDouble("canone_mensile"), queryResult.getString("pin"),
                                MagnusDAO.getInstance().getRelStatoCarteDAO().doRetriveActualState(queryResult.getString("num_carta_debito"), true),
                                MagnusDAO.getInstance().getContoCorrenteDAO().doRetriveByKey(queryResult.getString("num_cc")));
                    else
                        carta= new CartaProxy(queryResult.getString("num_carta_debito"), queryResult.getBoolean("stato_pagamento_online"), queryResult.getDate("data_creazione").toString(),
                                queryResult.getDate("data_scadenza").toString(), queryResult.getString("cvv"), queryResult.getBoolean("carta_fisica"),
                                queryResult.getInt("canone_mensile"), queryResult.getString("pin"), 0.0,
                                MagnusDAO.getInstance().getRelStatoCarteDAO().doRetriveActualState(queryResult.getString("num_carta_credito"), true), TipiCarte.DEBITO);
                }
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return carta;
    }

    @Override
    public boolean saveOrUpdate(Carte carta, TipiCarte tipo) {
        String query= "";

        if(tipo==TipiCarte.CREDITO)
            query= "insert into carta_di_credito(num_carta_credito, stato_pagamento_online, data_creazione, data_scadenza, cvv, carta_fisica, canone_mensile, num_cc, pin, fido) " +
                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) on conflict (num_carta_credito) do update set stato_pagamento_online = EXCLUDED.stato_pagamento_online, \" +\n" +
                    "                    \"data_creazione = EXCLUDED.data_creazione, data_scadenza = EXCLUDED.data_scadenza, \" +\n" +
                    "                    \"cvv = EXCLUDED.cvv, carta_fisica = EXCLUDED.carta_fisica, canone_mensile = EXCLUDED.canone_mensile, \" +\n" +
                    "                    \"pin = EXCLUDED.pin, id_stato = EXCLUDED.id_stato, num_cc = EXCLUDED.num_cc, fido = EXCLUDED.fido";
        else
            query= "";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean delete(Carte carta, boolean tipo) {
        String query;
        if(tipo){
            query = "DELETE FROM rel_stato_carta_credito WHERE num_carta = ?";
        }else{
            query = "DELETE FROM rel_stato_carta_debito WHERE num_carta = ?";

        }

        try {
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);
            statement.setString(1, carta.getNumCarta());

            if (statement.executeUpdate()>0  && eliminaCarta(carta, tipo)){
                return true;
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

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
                        queryResult.getString("pin"), queryResult.getDouble("fido"), MagnusDAO.getInstance().getRelStatoCarteDAO().doRetriveActualState(queryResult.getString("num_carta_credito"), true), TipiCarte.CREDITO));
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
                result.add(new CartaProxy(queryResult.getString("num_carta_debito"), queryResult.getBoolean("stato_pagamento_online"), queryResult.getDate("data_creazione").toString(),
                        queryResult.getDate("data_scadenza").toString(), queryResult.getString("cvv"), queryResult.getBoolean("carta_fisica"), queryResult.getInt("canone_mensile"),
                        queryResult.getString("pin"), 0.0, MagnusDAO.getInstance().getRelStatoCarteDAO().doRetriveActualState(queryResult.getString("num_carta_credito"), true), TipiCarte.DEBITO));
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean eliminaCarta(Carte carta, boolean tipo) {
        String carteQuery;

        if(tipo) {
            carteQuery = "DELETE FROM carta_di_credito WHERE num_carta_credito = ?";
        }else{
            carteQuery = "DELETE FROM carta_di_debito WHERE num_carta_debito = ?";

        }
        try {
            PreparedStatement statement = DbConn.getConnection().prepareStatement(carteQuery);
            statement.setString(1, carta.getNumCarta());

            if (statement.executeUpdate() > 0)
                return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
