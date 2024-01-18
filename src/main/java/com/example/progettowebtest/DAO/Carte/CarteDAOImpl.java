package com.example.progettowebtest.DAO.Carte;

import com.example.progettowebtest.Connection.DbConn;
import com.example.progettowebtest.Model.Carte.Carta;
import com.example.progettowebtest.Model.Carte.CartaCredito;
import com.example.progettowebtest.Model.Proxy.TipoTransazione;
import com.example.progettowebtest.Model.Proxy.Transazione;
import com.example.progettowebtest.Model.Proxy.TransazioneProxy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class CarteDAOImpl implements CarteDAO{
    @Override
    public Vector<Carta> doRetriveAll() {
        return null;
    }

    @Override
    public Vector<Carta> doRetriveAllForCC(String numCC) {
        Vector<Carta> result= new Vector<>();

        prendiCarteCredito(result, numCC);
        prendicarteDebito(result, numCC);

        return result;
    }

    @Override
    public Carta doRetriveByKey(String numCarta, boolean tipo) {
        return null;
    }

    @Override
    public boolean saveOrUpdate(CartaCredito cartaCred) {
        return false;
    }

    @Override
    public boolean delete(CartaCredito cartaCred) {
        return false;
    }


    //Metodi di servizio
    private void prendiCarteCredito(Vector<Carta> result, String numCC) {
        String query= "select r.data_transizione, b.importo, b.causale from bollettino as b, rel_cc_bollettino as r where b.id_bollettino= r.id_bollettino";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            ResultSet queryResult= statement.executeQuery();

            while(queryResult.next()) {
                result.add(new TransazioneProxy(queryResult.getInt("id_bollettino"), queryResult.getDate("data_transazione").toString(), queryResult.getDouble("importo"),
                        queryResult.getString("causale"), TipoTransazione.BOLLETTINO));
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void prendicarteDebito(Vector<Carta> result, String numCC) {

    }
}
