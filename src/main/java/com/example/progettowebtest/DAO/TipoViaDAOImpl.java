package com.example.progettowebtest.DAO;

import com.example.progettowebtest.Connection.DbConnection;
import com.example.progettowebtest.Model.TipoVia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class TipoViaDAOImpl implements TipoViaDAO{
    private static TipoViaDAOImpl instance;
    private TipoViaDAOImpl() {}
    public static TipoViaDAOImpl getInstance() {
        if(instance==null)
            instance= new TipoViaDAOImpl();
        return instance;
    }

    @Override
    public Vector<TipoVia> doRetriveAll() {
        return null;
    }

    @Override
    public TipoVia doRetriveByKey(int idVia) {
        TipoVia result= null;

        try{
            String query= "select * from tipo_via where id_via= "+ idVia;
            PreparedStatement statement= DbConnection.getInstance().prepareStatement(query);
            ResultSet queryResult= statement.executeQuery();

            if(!queryResult.wasNull())
                result= new TipoVia(queryResult.getInt("id_via"), queryResult.getString("tipologia"));

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public TipoVia doRetriveByAttribute(String tipo) {
        TipoVia result= null;
        try{
            String query= "select * from tipo_via where tipologia= "+ tipo;
            PreparedStatement statement= DbConnection.getInstance().prepareStatement(query);
            ResultSet queryResult= statement.executeQuery();

            result= new TipoVia(queryResult.getInt("id_via"), queryResult.getString("tipologia"));

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(TipoVia tipo) {
        return false;
    }

    @Override
    public boolean delete(TipoVia tipo) {
        return false;
    }
}
