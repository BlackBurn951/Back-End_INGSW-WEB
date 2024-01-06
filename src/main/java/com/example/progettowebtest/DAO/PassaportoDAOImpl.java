package com.example.progettowebtest.DAO;

import com.example.progettowebtest.Connection.DbConnection;
import com.example.progettowebtest.Model.CartaIdentita;
import com.example.progettowebtest.Model.Passaporto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class PassaportoDAOImpl implements PassaportoDAO{
    @Override
    public Vector<Passaporto> doRetriveAll() {
        return null;
    }

    @Override
    public Passaporto doRetriveByKey(String numIdentificativo) {
        Passaporto result = null;
        try{
            String query = "select * from passaporto where num_identificativo= "+ numIdentificativo;
            PreparedStatement statement = DbConnection.getInstance().prepareStatement(query);
            ResultSet queryResult = statement.executeQuery();

            result = new Passaporto(queryResult.getString("nome"), queryResult.getString("cognome"), queryResult.getString("nazionalità"),
                    queryResult.getString("comune_di_nascita"), queryResult.getString("sesso"), queryResult.getString("provincia_di_nascita"),
                    queryResult.getDate("data_di_nascita").toString(), queryResult.getString("num_passaporto"), queryResult.getDate("data_di_emissione").toString(),
                    queryResult.getDate("data_di_scadenza").toString(), queryResult.getString("comune_di_rilascio"));
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(Passaporto passa) {
        return false;
    }

    @Override
    public boolean delete(Passaporto passa) {
        return false;
    }
}
