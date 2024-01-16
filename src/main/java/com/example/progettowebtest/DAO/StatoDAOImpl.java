package com.example.progettowebtest.DAO;

import com.example.progettowebtest.Connection.DbConn;
import com.example.progettowebtest.Model.Stato;
import com.example.progettowebtest.Model.ValoriStato;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class StatoDAOImpl implements StatoDAO{
    public  StatoDAOImpl() {}

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
    public Stato doRetriveByAttribute(ValoriStato val) {
        Stato result= null;
        String query= "select * from stato where stato= ?";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            if(val==ValoriStato.ATTIVO)
                statement.setString(1, "attivo");
            else if(val==ValoriStato.SOSPESO)
                statement.setString(1, "sospeso");
            else
                statement.setString(1, "chiuso");

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
