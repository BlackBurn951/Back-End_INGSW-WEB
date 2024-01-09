package com.example.progettowebtest.DAO.Indirizzo;

import com.example.progettowebtest.Connection.DatabaseConfig;
import com.example.progettowebtest.Connection.DbConn;
import com.example.progettowebtest.Connection.DbConnection;
import com.example.progettowebtest.Model.ColonneDatiComune;
import com.example.progettowebtest.Model.DatiComune;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public class DatiComuneDAOImpl implements DatiComuneDAO{
    private static DatiComuneDAOImpl instance;
    private DatiComuneDAOImpl() {}
    public static DatiComuneDAOImpl getInstance() {
        if(instance==null)
            instance= new DatiComuneDAOImpl();
        return instance;
    }

    @Override
    public Vector<DatiComune> doRetriveAll() {
        Vector<DatiComune> resultList = new Vector<>();

        try {
            String query = "select * from dati_comune";
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);
            ResultSet queryResult = statement.executeQuery();

            while (queryResult.next()) {
                DatiComune data = new DatiComune(queryResult.getInt("id_comune"), queryResult.getString("nome_comune"), queryResult.getString("cap"),
                        queryResult.getString("provincia"), queryResult.getString("regione"));
                resultList.add(data);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }


    @Override
    public DatiComune doRetriveByKey(int idComune) {
        DatiComune result= null;

        try{
            String query= "select * from dati_comune where id_comune= "+ idComune;
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            ResultSet queryResult= statement.executeQuery();

            if(!queryResult.wasNull())
                result= new DatiComune(queryResult.getInt("id_comune"), queryResult.getString("nome_comune"),
                        queryResult.getString("cap"), queryResult.getString("provincia"), queryResult.getString("regione"));

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Vector<DatiComune> doRetriveByAttribute(String att, ColonneDatiComune val) {
        Vector<DatiComune> result = new Vector<>();

        String query= "";
        try{
            switch (val) {
                case NOME_COMUNE ->  query= "select * from dati_comune where nome_comune= "+ att;
                case CAP -> query= "select * from dati_comune where cap= "+ att;
                case PROVINCIA ->  query= "select * from dati_comune where provincia= "+ att;
                case REGIONE ->     query= "select * from dati_comune where regione= "+ att;
            }
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);

            ResultSet queryResult= statement.executeQuery();
            while(queryResult.next()) {
                result.add(new DatiComune(queryResult.getInt("id_comune"), queryResult.getString("nome_comune"),
                        queryResult.getString("cap"), queryResult.getString("provincia"), queryResult.getString("regione")));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<String> restituisciCitta(String citta) {
        Vector<String> result= new Vector<>();
        String query = "SELECT nome_comune FROM dati_comune WHERE LOWER(nome_comune) LIKE LOWER('" + citta + "') || '%' LIMIT 20";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            ResultSet queryResult= statement.executeQuery();

            while(queryResult.next()) {
                result.add(queryResult.getString("nome_comune"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(DatiComune comune) {
        boolean result = true;

        try {
            String query = "INSERT INTO dati_comune (nome_comune, cap, provincia, regione) VALUES (?, ?, ?, ?)" +
                    " ON CONFLICT (nome_comune, cap, provincia, regione) DO NOTHING";
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);

            statement.setString(1, comune.getNomeComune());
            statement.setString(2, comune.getCap());
            statement.setString(3, comune.getProvincia());
            statement.setString(4, comune.getRegione());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            result= false;
        }
        return result;
    }

    @Override
    public boolean delete(DatiComune comune) {
        boolean result = false;

        try {
            String query = "DELETE FROM dati_comune WHERE id_comune= "+comune.getIdComune();
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);

            if (statement.executeUpdate()>0)
                result = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
