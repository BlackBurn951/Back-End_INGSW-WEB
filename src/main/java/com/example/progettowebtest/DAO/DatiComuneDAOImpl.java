package com.example.progettowebtest.DAO;

import com.example.progettowebtest.Connection.DbConnection;
import com.example.progettowebtest.Model.ColonneDatiComune;
import com.example.progettowebtest.Model.DatiComune;
import com.example.progettowebtest.Model.TipoVia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class DatiComuneDAOImpl implements DatiComuneDAO{
    @Override
    public Vector<DatiComune> doRetriveAll() {
        return null;
    }

    @Override
    public DatiComune doRetriveByKey(int idComune) {
        DatiComune result= null;
        try{
            String query= "select * from dati_comune where id_comune= "+ idComune;
            PreparedStatement statement= DbConnection.getInstance().prepareStatement(query);
            ResultSet queryResult= statement.executeQuery();

            result= new DatiComune(queryResult.getInt("id_comune"), queryResult.getString("nome_comune"),
                    queryResult.getString("cap"), queryResult.getString("provincia"), queryResult.getString("regione"));

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public DatiComune doRetriveByAttribute(String att, ColonneDatiComune val) {
        DatiComune result= null;
        String query= "";
        try{
            switch (val) {
                case NOME_COMUNE ->  query= "select * from dati_comune where nome_comune= "+ att;
                case CAP -> query= "select * from dati_comune where cap= "+ att;
                case PROVINCIA ->  query= "select * from dati_comune where provincia= "+ att;
                case REGIONE ->     query= "select * from dati_comune where regione= "+ att;
            }
            PreparedStatement statement= DbConnection.getInstance().prepareStatement(query);
            ResultSet queryResult= statement.executeQuery();

            result= new DatiComune(queryResult.getInt("id_comune"), queryResult.getString("nome_comune"),
                    queryResult.getString("cap"), queryResult.getString("provincia"), queryResult.getString("regione"));

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(DatiComune comune) {
        return false;
    }

    @Override
    public boolean delete(DatiComune comune) {
        return false;
    }
}
