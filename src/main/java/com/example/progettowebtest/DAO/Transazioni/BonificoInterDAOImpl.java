package com.example.progettowebtest.DAO.Transazioni;
/*
import com.example.progettowebtest.Connection.DbConn;
import com.example.progettowebtest.DAO.MagnusDAO;
import com.example.progettowebtest.Model.Proxy.TipoTransazione;
import com.example.progettowebtest.Model.Proxy.Transazione;
import com.example.progettowebtest.Model.Proxy.TransazioneProxy;
import com.example.progettowebtest.Model.Transazioni.Bollettino;
import com.example.progettowebtest.Model.Transazioni.BonificoInter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class BonificoInterDAOImpl implements BonificoInterDAO{
    @Override
    public Vector<Transazione> doRetriveAll() {
        return null;
    }

    @Override
    public Vector<Transazione> doRetriveAllForCC(String numCC) {
        Vector<Transazione> result= new Vector<>();
        String query= "select r.data_transazione, b.importo, b.causale from bonifico_internazionale as b, rel_cc_bon_int as r where b.id_internazionale= r.id_internazionale";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            ResultSet queryResult= statement.executeQuery();

            while(queryResult.next()) {
                result.add(new TransazioneProxy(queryResult.getInt("id_internazionale"), queryResult.getDate("data_transazione").toString(), queryResult.getDouble("importo"),
                        queryResult.getString("casuale"), TipoTransazione.BONIFICOINTER));
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
            query= "select * from bonifico_internazionale as b, rel_cc_bon_int as r where b.id_internazionale= r.id_internazionale";
        else
            query= "select r.data_transazione, b.importo, b.causale from bonifico_internazionale as b, rel_cc_bon_int as r where b.id_internazionale= r.id_internazionale";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            ResultSet queryResult= statement.executeQuery();

            if(proxy)
                return new BonificoInter(queryResult.getDate("data_transazione").toString(), queryResult.getDouble("costo_commissione"),
                        queryResult.getBoolean("esito"), queryResult.getInt("id_internazionale"), queryResult.getString("nome_beneficiario"), queryResult.getString("cognome_beneficiario"),
                        queryResult.getDouble("importo"), queryResult.getString("casuale"), queryResult.getString("iban_destinatario"), );
            else
                return new TransazioneProxy(queryResult.getInt("id_bollettino"), queryResult.getDate("data_transazione").toString(), queryResult.getDouble("importo"),
                        queryResult.getString("casuale"), TipoTransazione.BOLLETTINO);

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean saveOrUpdate(BonificoInter bonInt) {
        return false;
    }

    @Override
    public boolean delete(BonificoInter bonInt) {
        return false;
    }
}
*/