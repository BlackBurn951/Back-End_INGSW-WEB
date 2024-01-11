package com.example.progettowebtest.DAO.Carte;

import com.example.progettowebtest.Connection.DbConn;
import com.example.progettowebtest.DAO.ContoCorrente_StatoConto.ContoCorrenteDAO;
import com.example.progettowebtest.DAO.ContoCorrente_StatoConto.ContoCorrenteDAOImpl;
import com.example.progettowebtest.DAO.StatoDAO;
import com.example.progettowebtest.DAO.StatoDAOImpl;
import com.example.progettowebtest.Model.Carte.Carta;
import com.example.progettowebtest.Model.Carte.CartaCredito;
import com.example.progettowebtest.Model.ContoCorrente.ContoCorrente;
import com.example.progettowebtest.Model.Stato;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class CartaCreditoDAOImpl implements CartaCreditoDAO{
    private static CartaCreditoDAOImpl instance;
    private ContoCorrenteDAO contoDAO= ContoCorrenteDAOImpl.getInstance();
    private StatoDAO statoDAO= StatoDAOImpl.getInstance();



    private CartaCreditoDAOImpl() {}

    public static CartaCreditoDAOImpl getInstance() {
        if(instance==null)
            instance= new CartaCreditoDAOImpl();
        return instance;
    }

    @Override
    public Vector<CartaCredito> doRetriveAll() {
        Vector<CartaCredito> result = new Vector<>();

        try {
            String query = "SELECT * FROM carta_di_credito";
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);
            ResultSet queryResult = statement.executeQuery();

            while (queryResult.next()) {
                ContoCorrente contoCorrente = contoDAO.doRetrivebyKey(queryResult.getString("num_cc"));
                Stato stato = statoDAO.doRetrivebyKey(queryResult.getInt("id_stato"));
                CartaCredito cartaCredito = new CartaCredito(
                        queryResult.getString("num_carta_credito"),
                        queryResult.getBoolean("stato_pagamento_online"),
                        queryResult.getDate("data_creazione").toString(),
                        queryResult.getDate("data_scadenza").toString(),
                        queryResult.getString("cvv"),
                        queryResult.getBoolean("carta_fisica"),
                        queryResult.getInt("canone_mensile"),
                        queryResult.getString("pin"),
                        stato,
                        contoCorrente,
                        queryResult.getDouble("fido")
                );
                result.add(cartaCredito);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    @Override
    public CartaCredito doRetriveByKey(String numCarta) {
        CartaCredito result = null;

        try{
            String query = "select * from carta_di_credito where num_carta_credito= ?";

            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);

            ResultSet queryResult = statement.executeQuery();


            if(!queryResult.wasNull()){
                ContoCorrente contoCorrente = contoDAO.doRetrivebyKey(queryResult.getString("num_cc"));
                Stato stato = statoDAO.doRetrivebyKey(queryResult.getInt("id_stato"));
                CartaCredito cartaCredito = new CartaCredito(
                        queryResult.getString("num_carta_credito"),
                        queryResult.getBoolean("stato_pagamento_online"),
                        queryResult.getDate("data_creazione").toString(),
                        queryResult.getDate("data_scadenza").toString(),
                        queryResult.getString("cvv"),
                        queryResult.getBoolean("carta_fisica"),
                        queryResult.getInt("canone_mensile"),
                        queryResult.getString("pin"),
                        stato,
                        contoCorrente,
                        queryResult.getDouble("fido")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(CartaCredito cartaCred) {
        try {
            String query = "INSERT INTO carta_di_credito VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
                    + " ON CONFLICT (num_carta_credito) DO UPDATE "
                    + " SET stato_pagamento_online = ?, data_creazione = ?, data_scadenza = ?, cvv = ?, carta_fisica = ?, "
                    + " canone_mensile = ?, pin = ?, id_stato = ?, num_cc = ?, fido = ?";

            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);

            statement.setString(1, cartaCred.getNumCartaCredito());
            statement.setBoolean(2, cartaCred.isStatoPagamentoOnline());
            // Set other parameters accordingly

            statement.setBoolean(11, cartaCred.isStatoPagamentoOnline());
            // Set other parameters accordingly for the ON CONFLICT part

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(CartaCredito cartaCred) {
        try {
            String query = "DELETE FROM carta_di_credito WHERE num_carta_credito = ?";
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);
            statement.setString(1, cartaCred.getNumCartaCredito());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
