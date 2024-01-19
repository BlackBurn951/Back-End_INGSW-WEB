package com.example.progettowebtest.DAO.Transazioni;

import com.example.progettowebtest.Connection.DbConn;
import com.example.progettowebtest.DAO.MagnusDAO;
import com.example.progettowebtest.Model.Carte.Carta;
import com.example.progettowebtest.Model.Carte.Carte;
import com.example.progettowebtest.Model.Carte.TipiCarte;
import com.example.progettowebtest.Model.Proxy.TipoTransazione;
import com.example.progettowebtest.Model.Proxy.Transazione;
import com.example.progettowebtest.Model.Proxy.TransazioneProxy;
import com.example.progettowebtest.Model.Transazioni.BonificoInter;
import com.example.progettowebtest.Model.Transazioni.Deposito;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Vector;

public class DepositoDAOImpl implements DepositoDAO{
    @Override
    public Vector<Transazione> doRetriveAll() {
        return null;
    }

    @Override
    public Vector<Transazione> doRetriveAllForCC(String numCC) {
        Vector<Transazione> result= new Vector<>();
        String query= "select r.data_transazione, b.importo from deposito as b, rel_cc_deposito as r where b.id_deposito=r.id_deposito";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            ResultSet queryResult= statement.executeQuery();

            while(queryResult.next()) {
                result.add(new TransazioneProxy(queryResult.getInt("id_deposito"), queryResult.getDate("data_transazione").toString(), queryResult.getDouble("importo"),
                        "", TipoTransazione.DEPOSITO));
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
            query= "select * from deposito as b, rel_cc_deposito as r where b.id_deposito= r.id_deposito";
        else
            query= "select r.data_transazione, b.importo from deposito as b, rel_cc_deposito as r where b.id_deposito=r.id_deposito";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            ResultSet queryResult= statement.executeQuery();

            if(proxy && queryResult.next()) {
                Carte carta;
                if(queryResult.getString("num_carta_credito")==null)
                    carta= MagnusDAO.getInstance().getCarteDAO().doRetriveByKey(queryResult.getString("num_carta_credito"), TipiCarte.CREDITO, proxy);
                else
                    carta= MagnusDAO.getInstance().getCarteDAO().doRetriveByKey(queryResult.getString("num_carta_debito"), TipiCarte.DEBITO, proxy);
                return new Deposito(queryResult.getDate("data_transazione").toString(), queryResult.getDouble("costo_commissione"),
                        queryResult.getBoolean("esito"), queryResult.getInt("id_deposito"), queryResult.getDouble("importo"),
                        MagnusDAO.getInstance().getMezzoDAO().doRetriveByKey(queryResult.getInt("id_mezzo")), carta);
            }
            else if(queryResult.next())
                return new TransazioneProxy(queryResult.getInt("id_deposito"), queryResult.getDate("data_transazione").toString(), queryResult.getDouble("importo"),
                        queryResult.getString("causale"), TipoTransazione.DEPOSITO);

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean saveOrUpdate(Deposito depo, String numCC) {
        String query="insert into deposito(importo, id_mezzo, num_carta_credito, num_carta_debito) values(?, ?, ?, ?)";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);

            statement.setDouble(1, depo.getImporto());
            statement.setInt(2, depo.getMezzo().getIdMezzo());
            if(depo.getCartaEsecuzione().getClass().getSimpleName().equals("CartaCredito")) {
                statement.setString(3, depo.getCartaEsecuzione().getNumCarta());
                statement.setNull(4, Types.NULL);
            }
            else {
                statement.setNull(3, Types.NULL);
                statement.setString(4, depo.getCartaEsecuzione().getNumCarta());
            }

            if(statement.executeUpdate()>0 && inserisciRelazion(depo, numCC))
                return true;

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Transazione dep) {
        String query = "DELETE FROM rel_cc_deposito WHERE id_deposito = ?";

        try {
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);
            statement.setInt(1, dep.getId());

            if (statement.executeUpdate()>0 && eliminaTransazione(dep)) {
                return true;
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }



    //Metodi di servizio
    private boolean inserisciRelazion(Deposito dep, String numCC) {
        String query= "insert into rel_cc_deposito(data_transazione, costo_commissione, esito, id_deposito, num_cc) " +
                "values (?, ?, ?, ?, ?)";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);

            statement.setDate(1, dep.getDataTransazione());
            statement.setDouble(2, dep.getCostoTransazione());
            statement.setBoolean(3, dep.getEsito());
            statement.setInt(4, dep.getId());
            statement.setString(5, numCC);

            if(statement.executeUpdate()>0)
                return true;

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean eliminaTransazione(Transazione dep) {
        String depositoQuery = "DELETE FROM deposito WHERE id_deposito= ?";

        try {
            PreparedStatement statement = DbConn.getConnection().prepareStatement(depositoQuery);
            statement.setInt(1, dep.getId());

            if (statement.executeUpdate() > 0)
                return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
