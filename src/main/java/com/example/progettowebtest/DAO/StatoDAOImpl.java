package com.example.progettowebtest.DAO;

import com.example.progettowebtest.Connection.DbConn;
import com.example.progettowebtest.Model.Stato;
import com.example.progettowebtest.Model.TabelleCorelateStato;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class StatoDAOImpl implements StatoDAO{
    private static StatoDAOImpl instance= null;

    private  StatoDAOImpl() {}

    public static StatoDAOImpl getInstance() {
        if(instance==null)
            instance= new StatoDAOImpl();
        return instance;
    }

    @Override
    public Vector<Stato> doRetriveAll() {
        return null;
    }

    @Override
    public Stato doRetriveByKey(int id) {
        Stato result= null;
        String query= "select * from stato where id_stato= ?";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            statement.setInt(1, id);

            ResultSet queryResult= statement.executeQuery();

            if(!queryResult.wasNull())
                result = new Stato(queryResult.getInt("id_stato"), queryResult.getString("stato"));

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(Stato st) {
        return false;
    }

    @Override
    public boolean delete(Stato st) {
        return false;
    }
}
