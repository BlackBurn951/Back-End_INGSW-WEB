package com.example.progettowebtest.DAO.Transazioni;

import com.example.progettowebtest.Connection.DbConn;
import com.example.progettowebtest.Model.Proxy.Transazione;
import com.example.progettowebtest.Model.Transazioni.TipologiaBollettino;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class TipologiaBollettinoDAOImpl implements TipologiaBollettinoDAO{
    @Override
    public Vector<TipologiaBollettino> doRetriveAll() {
        return null;
    }

    @Override
    public Vector<Transazione> doRetriveAllForCC(String numCC) {
        return null;
    }

    @Override
    public TipologiaBollettino doRetriveByKey(int id) {
        TipologiaBollettino result= null;
        String query= "select * from tipologia_bollettino where id_tipologia_bollettino= ?";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            statement.setInt(1, id);

            ResultSet queryResult= statement.executeQuery();

            if(queryResult.next())
                result= new TipologiaBollettino(queryResult.getInt("id_tipologia_bollettino"), queryResult.getString("tipo"));

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public TipologiaBollettino doRetriveByAttribute(String tipo) {
        TipologiaBollettino result= null;
        String query= "select * from tipologia_bollettino where tipo= ?";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            statement.setString(1, tipo);

            ResultSet queryResult= statement.executeQuery();

            if(queryResult.next())
                result= new TipologiaBollettino(queryResult.getInt("id_tipologia_bollettino"), queryResult.getString("tipo"));

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(TipologiaBollettino bol, String numCC) {
        return false;
    }


    @Override
    public boolean delete(TipologiaBollettino bol) {
        return false;
    }
}
