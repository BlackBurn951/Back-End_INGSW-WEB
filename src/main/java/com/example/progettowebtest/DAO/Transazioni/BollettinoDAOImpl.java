package com.example.progettowebtest.DAO.Transazioni;

import com.example.progettowebtest.Connection.DbConn;
import com.example.progettowebtest.DAO.MagnusDAO;
import com.example.progettowebtest.Model.Proxy.TipoTransazione;
import com.example.progettowebtest.Model.Proxy.Transazione;
import com.example.progettowebtest.Model.Proxy.TransazioneProxy;
import com.example.progettowebtest.Model.Transazioni.Bollettino;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class BollettinoDAOImpl implements BollettinoDAO{
    @Override
    public Vector<Transazione> doRetriveAll() {
        return null;
    }

    @Override
    public Vector<Transazione> doRetriveAllForCC(String numCC) {
        Vector<Transazione> result= new Vector<>();
        String query= "select r.data_transizione, b.importo, b.causale from bollettino as b, rel_cc_bollettino as r where b.id_bollettino= r.id_bollettino";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            ResultSet queryResult= statement.executeQuery();

            while(queryResult.next()) {
                result.add(new TransazioneProxy(queryResult.getInt("id_bollettino"), queryResult.getDate("data_transazione").toString(), queryResult.getDouble("importo"),
                        queryResult.getString("casuale"), TipoTransazione.BOLLETTINO));
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Transazione doRetriveByKey(int id, boolean proxy) {
        String query;
        if(proxy)
            query= "select * from bollettino as b, rel_cc_bollettino as r where b.id_bollettino= r.id_bollettino";
        else
            query= "select r.data_transizione, b.importo, b.causale from bollettino as b, rel_cc_bollettino as r where b.id_bollettino= r.id_bollettino";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            ResultSet queryResult= statement.executeQuery();

            if(proxy)
                return new Bollettino(queryResult.getDate("data_transazione").toString(), queryResult.getDouble("costo_commissione"),
                        queryResult.getBoolean("esito"), queryResult.getInt("id_bollettino"), queryResult.getDouble("importo"), queryResult.getString("casuale"),
                        queryResult.getString("num_cc_destinazione"), MagnusDAO.getInstance().getTipologiaBollettinoDAO().doRetriveByKey(queryResult.getInt("id_tipologia_bollettino")));
            else
                return new TransazioneProxy(queryResult.getInt("id_bollettino"), queryResult.getDate("data_transazione").toString(), queryResult.getDouble("importo"),
                        queryResult.getString("casuale"), TipoTransazione.BOLLETTINO);

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean saveOrUpdate(Bollettino bol, String numCC) {

        String query="insert into bollettino(importo, causale, num_cc_destinazione, id_tipologia_bollettino)" +
                "values(?, ?, ?, ?)";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);

            statement.setDouble(1, bol.getImporto());
            statement.setString(2, bol.getCausale());
            statement.setString(3, bol.getNumCcDest());
            statement.setInt(4, bol.getTipoBol().getIdTipoBol());

            if(statement.executeUpdate()>0 && inserisciRelazion(bol, numCC))
                return true;


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
    private boolean inserisciRelazion(Bollettino bol, String numCC) {
        String query= "insert into rel_cc_bollettino(data_transizione, costo_commissione, esito, id_bollettino, num_cc) " +
                "values (?, ?, ?, ?, ?)";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);

            statement.setDate(1, bol.getDataTransazione());
            statement.setDouble(2, bol.getCostoTransazione());
            statement.setBoolean(3, bol.getEsito());
            statement.setInt(4, bol.getId());
            statement.setString(5, numCC);

            if(statement.executeUpdate()>0)
                return true;

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }
}
