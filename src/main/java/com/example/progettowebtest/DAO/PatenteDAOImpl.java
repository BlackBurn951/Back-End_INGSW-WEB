package com.example.progettowebtest.DAO;

import com.example.progettowebtest.Connection.DbConnection;
import com.example.progettowebtest.Model.CartaIdentita;
import com.example.progettowebtest.Model.Patente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class PatenteDAOImpl implements PatenteDAO{
    @Override
    public Vector<Patente> doRetriveAll() {
        return null;
    }

    @Override
    public Patente doRetriveByKey(String numIdentificativo) {
        Patente result = null;
        try{
            String query = "select * from patente where num_identificativo= "+ numIdentificativo;
            PreparedStatement statement = DbConnection.getInstance().prepareStatement(query);
            ResultSet queryResult = statement.executeQuery();

            result = new Patente(queryResult.getString("nome"), queryResult.getString("cognome"), queryResult.getString("nazionalità"),
                    queryResult.getString("comune_di_nascita"), queryResult.getString("sesso"), queryResult.getString("provincia_di_nascita"),
                    queryResult.getDate("data_di_nascita").toString(), queryResult.getString("num_identificativo"), queryResult.getDate("data_di_emissione").toString(),
                    queryResult.getDate("data_di_scadenza").toString(), queryResult.getString("comune_di_rilascio"));
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(Patente pat) {
        return false;
    }

    @Override
    public boolean delete(Patente pat) {
        return false;
    }
}
