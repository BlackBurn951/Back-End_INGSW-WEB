package com.example.progettowebtest.DAO.Utente_Documenti;

import com.example.progettowebtest.Connection.DbConn;
import com.example.progettowebtest.Connection.DbConnection;
import com.example.progettowebtest.Model.Patente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class PatenteDAOImpl implements PatenteDAO{
    private static PatenteDAOImpl instance;
    public PatenteDAOImpl() {}
    public static PatenteDAOImpl getInstance() {
        if(instance==null)
            instance= new PatenteDAOImpl();
        return instance;
    }

    @Override
    public Vector<Patente> doRetriveAll() {
        Vector<Patente> resultList = new Vector<>();

        try {
            String query = "SELECT * FROM patente";
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);
            ResultSet queryResult = statement.executeQuery();

            while (queryResult.next()) {
                Patente patente = new Patente(queryResult.getString("nome"), queryResult.getString("cognome"), queryResult.getString("nazionalità"),
                        queryResult.getString("comune_di_nascita"), queryResult.getString("sesso"), queryResult.getString("provincia_di_nascita"),
                        queryResult.getDate("data_di_nascita").toString(), queryResult.getString("num_patente"), queryResult.getDate("data_di_emissione").toString(),
                        queryResult.getDate("data_di_scadenza").toString(), queryResult.getString("autorità_emittente"));
                resultList.add(patente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public Patente doRetriveByKey(String numIdentificativo) {
        Patente result = null;

        try{
            String query = "select * from patente where num_patente= "+ numIdentificativo;
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);
            ResultSet queryResult = statement.executeQuery();

            if(!queryResult.wasNull())
                result = new Patente(queryResult.getString("nome"), queryResult.getString("cognome"), "Italiana",
                        queryResult.getString("comune_di_nascita"), queryResult.getString("sesso"), queryResult.getString("provincia_di_nascita"),
                        queryResult.getDate("data_di_nascita").toString(), queryResult.getString("num_patente"), queryResult.getDate("data_di_emissione").toString(),
                        queryResult.getDate("data_di_scadenza").toString(), queryResult.getString("autorità_emittente"));

        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(Patente pat) {
        boolean result = true;

        try {
            String query = "INSERT INTO patente (num_patente, nome, cognome, comune_di_nascita, sesso, provincia_di_nascita, data_di_nascita, data_di_emissione, data_di_scadenza, autorità_emittente) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (num_patente) DO UPDATE SET " +
                    "nome = EXCLUDED.nome, cognome = EXCLUDED.cognome, comune_di_nascita = EXCLUDED.comune_di_nascita, sesso = EXCLUDED.sesso, provincia_di_nascita = EXCLUDED.provincia_di_nascita, " +
                    "data_di_nascita = EXCLUDED.data_di_nascita, data_di_emissione = EXCLUDED.data_di_emissione, data_di_scadenza = EXCLUDED.data_di_scadenza, autorità_emittente = EXCLUDED.autorità_emittente";

            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);

            statement.setString(1, pat.getNumIdentificativo());
            statement.setString(2, pat.getNome());
            statement.setString(3, pat.getCognome());
            statement.setString(4, pat.getComuneNascita());
            statement.setString(5, pat.getSesso());
            statement.setString(6, pat.getProvNascita());
            statement.setDate(7, pat.getDataNascita());
            statement.setDate(8, pat.getDataEmissione());
            statement.setDate(9, pat.getDataScadenza());
            statement.setString(10, pat.getEntitaRilascio());

        } catch (SQLException e) {
            e.printStackTrace();
            result= false;
        }
        return result;
    }


    @Override
    public boolean delete(Patente pat) {
        boolean result = false;

        try {
            String query = "DELETE FROM patente WHERE num_patente = "+ pat.getNumIdentificativo();
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);

            if (statement.executeUpdate()>0)
                result = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
