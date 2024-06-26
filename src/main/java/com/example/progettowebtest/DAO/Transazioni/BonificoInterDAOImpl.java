package com.example.progettowebtest.DAO.Transazioni;

import com.example.progettowebtest.Connection.DbConn;
import com.example.progettowebtest.Model.Proxy.TipoTransazione;
import com.example.progettowebtest.Model.Proxy.Transazione;
import com.example.progettowebtest.Model.Proxy.TransazioneProxy;
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
        String query= "select b.id_internazionale, r.data_transazione, b.importo, r.esito, b.causale from bonifico_internazionale as b, rel_cc_bon_int as r where b.id_internazionale= r.id_internazionale";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            ResultSet queryResult= statement.executeQuery();

            while(queryResult.next()) {
                result.add(new TransazioneProxy(queryResult.getInt("id_internazionale"), queryResult.getDate("data_transazione").toString(), queryResult.getDouble("importo"),
                        queryResult.getString("causale"), queryResult.getBoolean("esito"), TipoTransazione.BONIFICOINTER));
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int retriveLastId(){
        String query= "select max(id_internazionale) as id from bonifico_internazionale";
        int max;
        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            ResultSet queryResult= statement.executeQuery();

            if(queryResult.next()){
                max = queryResult.getInt("id");
                return max;

            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public Transazione doRetriveByKey(int id, boolean proxy) {
        Transazione bonInt= null;
        String query;
        if(proxy)
            query= "select * from bonifico_internazionale as b, rel_cc_bon_int as r where b.id_internazionale= ? and b.id_internazionale= r.id_internazionale";
        else
            query= "select b.id_internazionale, r.data_transazione, b.importo, r.esito, b.causale from bonifico_internazionale as b, rel_cc_bon_int as r where b.id_internazionale= ? and b.id_internazionale=r.id_internazionale";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            statement.setInt(1,id);

            ResultSet queryResult= statement.executeQuery();

            if(proxy && queryResult.next()) {
                bonInt= new BonificoInter(queryResult.getDate("data_transazione").toString(), queryResult.getDouble("costo_commissione"),
                        queryResult.getBoolean("esito"), queryResult.getString("nome_beneficiario"), queryResult.getString("cognome_beneficiario"),
                        queryResult.getDouble("importo"), queryResult.getString("causale"), queryResult.getString("iban_destinatario"), queryResult.getString("valuta_pagamento"), queryResult.getString("paese_destinatario"));
                bonInt.setId(queryResult.getInt("id_internazionale"));

                return bonInt;
            }
            else if(queryResult.next())
                return new TransazioneProxy(queryResult.getInt("id_internazionale"), queryResult.getDate("data_transazione").toString(), queryResult.getDouble("importo"),
                        queryResult.getString("causale"),queryResult.getBoolean("esito"), TipoTransazione.BONIFICOINTER);

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean saveOrUpdate(BonificoInter bonInt, String numCC) {
        String query="insert into bonifico_internazionale(nome_beneficiario, cognome_beneficiario, importo, causale, " +
                "iban_destinatario, valuta_pagamento, paese_destinatario) values(?, ?, ?, ?, ?, ?, ?)";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);

            statement.setString(1, bonInt.getNomeBeneficiario());
            statement.setString(2, bonInt.getCognomeBeneficiario());
            statement.setDouble(3, bonInt.getImporto());
            statement.setString(4, bonInt.getCausale());
            statement.setString(5, bonInt.getIbanDestinatario());
            statement.setString(6, bonInt.getValutaPagamento());
            statement.setString(7, bonInt.getPaeseDestinatario());



            if(statement.executeUpdate()>0) {
                int id= retriveLastId();
                if(id!=0) {
                    bonInt.setId(id);
                    return inserisciRelazione(bonInt, numCC);
                }
                else
                    return false;
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM rel_cc_bon_int WHERE id_internazionale = ?";

        try {
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);
            statement.setInt(1, id);

            if (statement.executeUpdate()>0  && eliminaTransazione(id)) {
                return true;
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    //Metodi di servizio
    private boolean inserisciRelazione(BonificoInter bon, String numCC) {
        String query= "insert into rel_cc_bon_int(data_transazione, costo_commissione, esito, id_internazionale, num_cc) " +
                "values (?, ?, ?, ?, ?)";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);

            statement.setDate(1, bon.getDataTransazione());
            statement.setDouble(2, bon.getCostoTransazione());
            statement.setBoolean(3, bon.getEsito());
            statement.setInt(4, bon.getId());
            statement.setString(5, numCC);

            if(statement.executeUpdate()>0)
                return true;

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean eliminaTransazione(int id) {
        String bonificoQuery = "DELETE FROM bonifico_internazionale WHERE id_internazionale= ?";

        try {
            PreparedStatement statement = DbConn.getConnection().prepareStatement(bonificoQuery);
            statement.setInt(1, id);

            if (statement.executeUpdate() > 0)
                return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
