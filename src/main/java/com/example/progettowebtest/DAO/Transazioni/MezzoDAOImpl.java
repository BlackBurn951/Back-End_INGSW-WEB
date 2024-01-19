package com.example.progettowebtest.DAO.Transazioni;

import com.example.progettowebtest.Connection.DbConn;
import com.example.progettowebtest.DAO.MagnusDAO;
import com.example.progettowebtest.Model.Proxy.TipoTransazione;
import com.example.progettowebtest.Model.Proxy.TransazioneProxy;
import com.example.progettowebtest.Model.Transazioni.BonificoInter;
import com.example.progettowebtest.Model.Transazioni.Deposito;
import com.example.progettowebtest.Model.Transazioni.Mezzo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class MezzoDAOImpl implements MezzoDAO{

    @Override
    public Vector<Mezzo> doRetriveAll() {
        return null;
    }

    @Override
    public Mezzo doRetriveByKey(int id) {
        String query= "select * from mezzo where id_mezzo= ?";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            ResultSet queryResult= statement.executeQuery();

            if(queryResult.next())
                return new Mezzo(queryResult.getInt("id_mezzo"), queryResult.getString("tipologia"));

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean saveOrUpdate(BonificoInter bonInt, String numCC) {
        return false;
    }

    @Override
    public boolean delete(BonificoInter bonInt) {
        return false;
    }
}
