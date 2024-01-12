package com.example.progettowebtest.DAO.Indirizzo;

import com.example.progettowebtest.Connection.DbConn;
import com.example.progettowebtest.Model.Indirizzo.TipoVia;

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
        Vector<TipoVia> resultList = new Vector<>();

        try {
            String query = "SELECT * FROM tipo_via";
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);
            ResultSet queryResult = statement.executeQuery();

            while (queryResult.next()) {
                TipoVia tipoVia = new TipoVia(queryResult.getInt("id_via"), queryResult.getString("tipologia"));
                resultList.add(tipoVia);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public TipoVia doRetriveByKey(int idVia) {
        TipoVia result= null;

        try{
            String query= "select * from tipo_via where id_via= ?";
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            statement.setInt(1, idVia);

            ResultSet queryResult= statement.executeQuery();

            if(queryResult.next())
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
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            ResultSet queryResult= statement.executeQuery();

            result= new TipoVia(queryResult.getInt("id_via"), queryResult.getString("tipologia"));

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(TipoVia tipo) {
        boolean result = true;

        try {
            String query = "INSERT INTO tipo_via (tipologia) VALUES (?) ON CONFLICT (tipologia) DO NOTHING";

            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);
            statement.setString(1, tipo.getTipoVia());

        } catch (SQLException e) {
            e.printStackTrace();
            result= false;
        }
        return result;
    }

    @Override
    public boolean delete(TipoVia tipo) {
        boolean result = false;

        try {
            String query = "DELETE FROM tipo_via WHERE id_via = "+ tipo.getIdVia();
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);

            if (statement.executeUpdate()>0)
                result = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
