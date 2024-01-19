package com.example.progettowebtest.DAO.Transazioni;

import com.example.progettowebtest.Connection.DbConn;
import com.example.progettowebtest.DAO.MagnusDAO;
import com.example.progettowebtest.Model.Carte.Carta;
import com.example.progettowebtest.Model.Carte.Carte;
import com.example.progettowebtest.Model.Carte.TipiCarte;
import com.example.progettowebtest.Model.Proxy.TipoTransazione;
import com.example.progettowebtest.Model.Proxy.Transazione;
import com.example.progettowebtest.Model.Proxy.TransazioneProxy;
import com.example.progettowebtest.Model.Transazioni.Prelievo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Vector;

public class PrelievoDAOImpl implements PrelievoDAO {
    @Override
    public Vector<Transazione> doRetriveAll() {
        return null;
    }

    @Override
    public Vector<Transazione> doRetriveAllForCC(String numCC) {
        Vector<Transazione> result= new Vector<>();
        String query= "select r.data_transazione, b.importo from prelievo as b, rel_cc_prelievo as r where b.id_prelievo=r.id_prelievo";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            ResultSet queryResult= statement.executeQuery();

            while(queryResult.next()) {
                result.add(new TransazioneProxy(queryResult.getInt("id_prelievo"), queryResult.getDate("data_transazione").toString(), queryResult.getDouble("importo"),
                        queryResult.getString("causale"), TipoTransazione.PRELIEVO));
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public int retriveLastId(){
        String query= "select max(id_prelievo) as id from prelievo";
        int max;
        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            ResultSet queryResult= statement.executeQuery();

            if(queryResult.next()) {
                max = queryResult.getInt("id_prelievo");
                return  max;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public Transazione doRetriveByKey(int id, boolean proxy) {
        Transazione prel = null;
        String query;
        if(proxy)
            query= "select * from prelievo as b, rel_cc_prelievo as r where b.id_prelievo= r.id_prelievo";
        else
            query= "select r.data_transazione, b.importo from prelievo as b, rel_cc_prelievo as r where b.id_prelievo= r.id_prelievo";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            ResultSet queryResult= statement.executeQuery();

            if(proxy && queryResult.next()) {
                Carte carta;
                if(queryResult.getString("num_carta_credito")==null)
                    carta= MagnusDAO.getInstance().getCarteDAO().doRetriveByKey(queryResult.getString("num_carta_credito"), TipiCarte.CREDITO, false);
                else
                    carta= MagnusDAO.getInstance().getCarteDAO().doRetriveByKey(queryResult.getString("num_carta_debito"),TipiCarte.DEBITO, false);

                prel = new Prelievo(queryResult.getDate("data_transazione").toString(), queryResult.getDouble("costo_commissione"),
                        queryResult.getBoolean("esito"), queryResult.getDouble("importo"),
                        MagnusDAO.getInstance().getMezzoDAO().doRetriveByKey(queryResult.getInt("id_mezzo")), carta);

                prel.setId(id);
            }
                else if(queryResult.next())
                return new TransazioneProxy(queryResult.getInt("id_prelievo"), queryResult.getDate("data_transazione").toString(), queryResult.getDouble("importo"),
                        "", TipoTransazione.PRELIEVO);

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return prel;
    }

    @Override
    public boolean saveOrUpdate(Prelievo prel, String numCC) {
        String query="insert into prelievo(importo, id_mezzo, num_carta_credito, num_carta_debito) values(?, ?, ?, ?)";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);

            statement.setDouble(1, prel.getImporto());
            statement.setInt(2, prel.getMezzo().getIdMezzo());
            if(prel.getCartaEsecuzione().getClass().getSimpleName().equals("CartaCredito")) {
                statement.setString(3, prel.getCartaEsecuzione().getNumCarta());
                statement.setNull(4, Types.NULL);
            }
            else {
                statement.setNull(3, Types.NULL);
                statement.setString(4, prel.getCartaEsecuzione().getNumCarta());
            }

            int i = statement.executeUpdate();
            if(i >0){
                int id= retriveLastId();
                if(id!=0)
                    prel.setId(id);
                else
                    return false;
                if(inserisciRelazione(prel, numCC))
                    return true;
                else
                    return false;

            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean delete(Transazione prel) {
        String query = "DELETE FROM rel_cc_prelievo WHERE id_prelievo = ?";

        try {
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);
            statement.setInt(1, prel.getId());

            if (statement.executeUpdate()>0 && eliminaTransazione(prel)) {
                return true;
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }



    //Metodi di servizio
    private boolean inserisciRelazione(Prelievo prel, String numCC) {
        String query= "insert into rel_cc_prelievo(data_transazione, costo_commissione, esito, id_prelievo, num_cc) " +
                "values (?, ?, ?, ?, ?)";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);

            statement.setDate(1, prel.getDataTransazione());
            statement.setDouble(2, prel.getCostoTransazione());
            statement.setBoolean(3, prel.getEsito());
            statement.setInt(4, prel.getId());
            statement.setString(5, numCC);

            if(statement.executeUpdate()>0)
                return true;

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean eliminaTransazione(Transazione prel) {
        String prelievoQuery = "DELETE FROM prelievo WHERE id_prelievo = ?";

        try {
            PreparedStatement statement = DbConn.getConnection().prepareStatement(prelievoQuery);
            statement.setInt(1, prel.getId());

            if (statement.executeUpdate() > 0)
                return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
