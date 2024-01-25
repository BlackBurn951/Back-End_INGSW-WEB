package com.example.progettowebtest.DAO.Carte;

import com.example.progettowebtest.Connection.DbConn;
import com.example.progettowebtest.DAO.MagnusDAO;
import com.example.progettowebtest.Model.Carte.*;

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
        prendiCarteDebito(result, numCC);

        return result;
    }

    @Override
    public Vector<Carte> doRetriveAllCreditForCC(String numCC) {
        Vector<Carte> result= new Vector<>();

        prendiCarteCredito(result, numCC);
        return result;
    }

    @Override
    public Vector<Carte> doRetriveAllDebitForCC(String numCC) {
        Vector<Carte> result= new Vector<>();

        prendiCarteDebito(result, numCC);
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
                                queryResult.getDouble("canone_mensile"), queryResult.getString("pin"),
                                MagnusDAO.getInstance().getRelStatoCarteDAO().doRetriveActualState(queryResult.getString("num_carta_credito"), TipiCarte.CREDITO),
                                MagnusDAO.getInstance().getContoCorrenteDAO().doRetriveByKey(queryResult.getString("num_cc")),
                                queryResult.getDouble("fido"));
                    else
                        carta= new CartaProxy(queryResult.getString("num_carta_credito"), queryResult.getBoolean("stato_pagamento_online"), queryResult.getDate("data_creazione").toString(),
                                queryResult.getDate("data_scadenza").toString(), queryResult.getString("cvv"), queryResult.getBoolean("carta_fisica"),
                                queryResult.getDouble("canone_mensile"), queryResult.getString("pin"), queryResult.getDouble("fido"),
                                MagnusDAO.getInstance().getRelStatoCarteDAO().doRetriveActualState(queryResult.getString("num_carta_credito"), TipiCarte.CREDITO), TipiCarte.CREDITO);
                }
                else {
                    if(proxy)
                        carta= new CartaDebito(queryResult.getString("num_carta_debito"), queryResult.getBoolean("stato_pagamento_online"), queryResult.getDate("data_creazione").toString(),
                                queryResult.getDate("data_scadenza").toString(), queryResult.getString("cvv"), queryResult.getBoolean("carta_fisica"),
                                queryResult.getDouble("canone_mensile"), queryResult.getString("pin"),
                                MagnusDAO.getInstance().getRelStatoCarteDAO().doRetriveActualState(queryResult.getString("num_carta_debito"), TipiCarte.DEBITO),
                                MagnusDAO.getInstance().getContoCorrenteDAO().doRetriveByKey(queryResult.getString("num_cc")));
                    else
                        carta= new CartaProxy(queryResult.getString("num_carta_debito"), queryResult.getBoolean("stato_pagamento_online"), queryResult.getDate("data_creazione").toString(),
                                queryResult.getDate("data_scadenza").toString(), queryResult.getString("cvv"), queryResult.getBoolean("carta_fisica"),
                                queryResult.getDouble("canone_mensile"), queryResult.getString("pin"), 3000.0,
                                MagnusDAO.getInstance().getRelStatoCarteDAO().doRetriveActualState(queryResult.getString("num_carta_debito"), TipiCarte.DEBITO), TipiCarte.DEBITO);
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
                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) on conflict (num_carta_credito) do update set stato_pagamento_online = EXCLUDED.stato_pagamento_online," +
                    "data_creazione = EXCLUDED.data_creazione, data_scadenza = EXCLUDED.data_scadenza, cvv = EXCLUDED.cvv, carta_fisica = EXCLUDED.carta_fisica, canone_mensile = EXCLUDED.canone_mensile, " +
                    "pin = EXCLUDED.pin, num_cc = EXCLUDED.num_cc, fido = EXCLUDED.fido";
        else
            query= "insert into carta_di_debito (num_carta_debito, stato_pagamento_online, data_creazione, data_scadenza, cvv, carta_fisica, canone_mensile, num_cc, pin) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                    "ON CONFLICT (num_carta_debito) DO UPDATE SET stato_pagamento_online = EXCLUDED.stato_pagamento_online, data_creazione = EXCLUDED.data_creazione, data_scadenza = EXCLUDED.data_scadenza, " +
                    "cvv = EXCLUDED.cvv, carta_fisica = EXCLUDED.carta_fisica, canone_mensile = EXCLUDED.canone_mensile, pin = EXCLUDED.pin, num_cc = EXCLUDED.num_cc";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);

            statement.setString(1, carta.getNumCarta());
            statement.setBoolean(2, carta.isPagamentoOnline());
            statement.setDate(3, carta.getDataCreazione());
            statement.setDate(4, carta.getDataScadenza());
            statement.setString(5, carta.getCvv());
            statement.setBoolean(6, carta.isCartaFisica());
            statement.setDouble(7, carta.getCanoneMensile());
            statement.setString(8, carta.getContoRiferimento().getNumCC());
            statement.setString(9, carta.getPin());

            if (tipo == TipiCarte.CREDITO)
                statement.setDouble(10, carta.getFido());

            if(statement.executeUpdate()>0) {
                RelStatoCarta rel= new RelStatoCarta(carta.getDataCreazione().toString(), carta.getStatoCarta(), carta);
                if(MagnusDAO.getInstance().getRelStatoCarteDAO().saveOrUpdate(rel, tipo))
                    return true;
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Carte carta, TipiCarte tipo) {
        String query = "";

        if (tipo == TipiCarte.CREDITO)
            query = "DELETE FROM carta_di_credito WHERE num_carta_credito = ?";
        else
            query = "DELETE FROM carta_di_debito WHERE num_carta_debito = ?";

        try {
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);
            statement.setString(1, carta.getNumCarta());

            if (statement.executeUpdate() > 0)
                return true;

        } catch (SQLException e) {
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
                        queryResult.getDate("data_scadenza").toString(), queryResult.getString("cvv"), queryResult.getBoolean("carta_fisica"), queryResult.getDouble("canone_mensile"),
                        queryResult.getString("pin"), queryResult.getDouble("fido"), MagnusDAO.getInstance().getRelStatoCarteDAO().doRetriveActualState(queryResult.getString("num_carta_credito"), TipiCarte.CREDITO), TipiCarte.CREDITO));
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void prendiCarteDebito(Vector<Carte> result, String numCC) {
        String query= "select * from carta_di_debito where num_cc= ?";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            statement.setString(1, numCC);

            ResultSet queryResult= statement.executeQuery();

            while(queryResult.next()) {
                result.add(new CartaProxy(queryResult.getString("num_carta_debito"), queryResult.getBoolean("stato_pagamento_online"), queryResult.getDate("data_creazione").toString(),
                        queryResult.getDate("data_scadenza").toString(), queryResult.getString("cvv"), queryResult.getBoolean("carta_fisica"), queryResult.getDouble("canone_mensile"),
                        queryResult.getString("pin"), 3000.0, MagnusDAO.getInstance().getRelStatoCarteDAO().doRetriveActualState(queryResult.getString("num_carta_debito"), TipiCarte.DEBITO), TipiCarte.DEBITO));
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
