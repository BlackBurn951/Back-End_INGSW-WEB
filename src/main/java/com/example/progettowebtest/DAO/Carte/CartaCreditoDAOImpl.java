package com.example.progettowebtest.DAO.Carte;

import com.example.progettowebtest.Connection.DbConn;
import com.example.progettowebtest.DAO.ContoCorrente_Salva_Stato.ContoCorrenteDAO;
import com.example.progettowebtest.DAO.ContoCorrente_Salva_Stato.ContoCorrenteDAOImpl;
import com.example.progettowebtest.Model.Carte.Carta;
import com.example.progettowebtest.Model.Carte.CartaCredito;
import com.example.progettowebtest.Model.ContoCorrente.ContoCorrente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class CartaCreditoDAOImpl implements CartaCreditoDAO{

    private ContoCorrenteDAO contoDAO= ContoCorrenteDAOImpl.getInstance();


    @Override
    public Vector<CartaCredito> doRetriveAll() {
        return null;
    }

    @Override
    public CartaCredito doRetriveByKey(int numCarta) {
        CartaCredito result = null;

        try{
            String query = "select * from carta_di_credito where num_carta_credito= ?";
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);

            ResultSet queryResult = statement.executeQuery();

            if(!queryResult.wasNull()){
                ContoCorrente contoCorrente = contoDAO.doRetrivebyKey(queryResult.getString("num_cc"));
                CartaCredito cartaCredito = new CartaCredito(
                        queryResult.getString("num_carta_credito"),
                        queryResult.getBoolean("stato_pagamento_online"),
                        queryResult.getDate("data_creazione"),
                        queryResult.getDate("data_scadenza"),
                        queryResult.getString("cvv"),
                        queryResult.getBoolean("carta_fisica"),
                        queryResult.getInt("canone_mensile"),
                        queryResult.getString("pin"),
                        contoCorrente,
                        queryResult.getDouble("fido")
                )


            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean saveOrUpdate(CartaCredito cartaCred) {
        return false;
    }

    @Override
    public boolean delete(CartaCredito cartaCred) {
        return false;
    }
}
