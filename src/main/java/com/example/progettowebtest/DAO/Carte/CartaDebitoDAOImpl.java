package com.example.progettowebtest.DAO.Carte;

import com.example.progettowebtest.Connection.DbConn;
import com.example.progettowebtest.DAO.ContoCorrente_StatoConto.ContoCorrenteDAO;
import com.example.progettowebtest.DAO.ContoCorrente_StatoConto.ContoCorrenteDAOImpl;
import com.example.progettowebtest.DAO.StatoDAO;
import com.example.progettowebtest.DAO.StatoDAOImpl;
import com.example.progettowebtest.Model.Carte.CartaDebito;
import com.example.progettowebtest.Model.ContoCorrente.ContoCorrente;
import com.example.progettowebtest.Model.Stato;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class CartaDebitoDAOImpl implements CartaDebitoDAO {
    private static CartaDebitoDAOImpl instance;

    private ContoCorrenteDAO contoDAO = ContoCorrenteDAOImpl.getInstance();
    private StatoDAO statoDAO = StatoDAOImpl.getInstance();

    private CartaDebitoDAOImpl() {}

    public static CartaDebitoDAOImpl getInstance() {
        if (instance == null)
            instance = new CartaDebitoDAOImpl();
        return instance;
    }

    @Override
    public Vector<CartaDebito> doRetriveAll() {
        Vector<CartaDebito> carteDebitoList = new Vector<>();

        try {
            String query = "SELECT * FROM carta_di_debito";
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);
            ResultSet queryResult = statement.executeQuery();

            while (queryResult.next()) {
                ContoCorrente contoCorrente = contoDAO.doRetrivebyKey(queryResult.getString("num_cc"));
                Stato stato = statoDAO.doRetrivebyKey(queryResult.getInt("id_stato"));

                CartaDebito cartaDebito = new CartaDebito(
                        queryResult.getString("num_carta_debito"),
                        queryResult.getBoolean("stato_pagamento_online"),
                        queryResult.getDate("data_creazione").toString(),
                        queryResult.getDate("data_scadenza").toString(),
                        queryResult.getString("cvv"),
                        queryResult.getBoolean("carta_fisica"),
                        queryResult.getDouble("canone_mensile"),
                        queryResult.getString("pin"),
                        stato,
                        contoCorrente
                );
                carteDebitoList.add(cartaDebito);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return carteDebitoList;
    }

    @Override
    public CartaDebito doRetriveByKey(String numCarta) {
        CartaDebito result = null;

        try {
            String query = "SELECT * FROM carta_di_debito WHERE num_carta_debito = ?";
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);
            statement.setString(1, numCarta);

            ResultSet queryResult = statement.executeQuery();

            if (queryResult.next()) {
                ContoCorrente contoCorrente = contoDAO.doRetrivebyKey(queryResult.getString("num_cc"));
                Stato stato = statoDAO.doRetrivebyKey(queryResult.getInt("id_stato"));

                result = new CartaDebito(
                        queryResult.getString("num_carta_debito"),
                        queryResult.getBoolean("stato_pagamento_online"),
                        queryResult.getDate("data_creazione").toString(),
                        queryResult.getDate("data_scadenza").toString(),
                        queryResult.getString("cvv"),
                        queryResult.getBoolean("carta_fisica"),
                        queryResult.getDouble("canone_mensile"),
                        queryResult.getString("pin"),
                        stato,
                        contoCorrente
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public boolean saveOrUpdate(CartaDebito cartaDebito) {
        try {
            String query = "INSERT INTO carta_di_debito (num_carta_debito, stato_pagamento_online, data_creazione, " +
                    "data_scadenza, cvv, carta_fisica, canone_mensile, pin, id_stato, num_cc) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                    "ON CONFLICT (num_carta_debito) DO UPDATE SET stato_pagamento_online = EXCLUDED.stato_pagamento_online, " +
                    "data_creazione = EXCLUDED.data_creazione, data_scadenza = EXCLUDED.data_scadenza, " +
                    "cvv = EXCLUDED.cvv, carta_fisica = EXCLUDED.carta_fisica, canone_mensile = EXCLUDED.canone_mensile, " +
                    "pin = EXCLUDED.pin, id_stato = EXCLUDED.id_stato, num_cc = EXCLUDED.num_cc";
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);

            statement.setString(1, cartaDebito.getNumCartaDebito());
            statement.setBoolean(2, cartaDebito.isStatoPagamentoOnline());
            // Set other parameters accordingly

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(CartaDebito cartaDebito) {
        try {
            String query = "DELETE FROM carta_di_debito WHERE num_carta_debito = ?";
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);
            statement.setString(1, cartaDebito.getNumCartaDebito());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
