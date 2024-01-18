package com.example.progettowebtest.DAO.Transazioni;

import com.example.progettowebtest.Connection.DbConn;
import com.example.progettowebtest.Model.Transazioni.Bollettino;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class BollettinoDAOImpl implements BollettinoDAO{
    @Override
    public Vector<Bollettino> doRetriveAll() {
        return null;
    }

    @Override
    public Bollettino doRetriveByKey(int id, boolean proxy) {
        return null;
    }

    @Override
    public boolean saveOrUpdate(Bollettino bol, String numCC) {
        boolean result= false;
        String query="insert into bollettino(importo, causale, num_cc_destinazione, id_tipologia_bollettino)" +
                "values(?, ?, ?, ?)";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);

            statement.setDouble(1, bol.getImporto());
            statement.setString(2, bol.getCausale());
            statement.setString(3, bol.getNumCcDest());
            statement.setInt(4, bol.getTipoBol().getIdTipoBol());

            if(statement.executeUpdate()>0)
                inserisciRelazione(bol, numCC);

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Bollettino bol) {
        return false;
    }

    //Metodi di servizio
    private boolean inserisciRelazione(Bollettino bol, String numCC) {
        return true;
    }
}
