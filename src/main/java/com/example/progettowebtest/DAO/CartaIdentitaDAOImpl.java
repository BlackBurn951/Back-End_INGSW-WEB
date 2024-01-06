package com.example.progettowebtest.DAO;

import com.example.progettowebtest.Connection.DbConnection;
import com.example.progettowebtest.Model.CartaIdentita;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class CartaIdentitaDAOImpl implements CartaIdentitaDAO{

    @Override
    public Vector<CartaIdentita> doRetriveAll() {
        return null;
    }

    @Override
    public CartaIdentita doRetriveByKey(String numIdentificativo) {
        CartaIdentita result = null;
        try{
            String query = "select * from carta_di_identità where num_identificativo= "+ numIdentificativo;
            PreparedStatement statement = DbConnection.getInstance().prepareStatement(query);
            ResultSet queryResult = statement.executeQuery();

            result = new CartaIdentita(queryResult.getString("nome"), queryResult.getString("cognome"), queryResult.getString("nazionalità"),
                    queryResult.getString("comune_di_nascita"), queryResult.getString("sesso"), queryResult.getString("provincia_di_nascita"),
                    queryResult.getDate("data_di_nascita").toString(), queryResult.getString("num_identificativo"), queryResult.getDate("data_di_emissione").toString(),
                    queryResult.getDate("data_di_scadenza").toString(), queryResult.getString("comune_di_rilascio"));
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(CartaIdentita cd) {
        return false;
    }

    @Override
    public boolean delete(CartaIdentita cd) {
        return false;
    }
}
