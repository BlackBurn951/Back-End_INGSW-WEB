package com.example.progettowebtest.DAO.Utente_Documenti;

import com.example.progettowebtest.Connection.DbConn;
import com.example.progettowebtest.Model.Utente_Documenti.Patente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class PatenteDAOImpl implements PatenteDAO{
    public PatenteDAOImpl() {}

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
            String query = "select * from patente where num_patente=?";
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);

            statement.setString(1, numIdentificativo);
            ResultSet queryResult = statement.executeQuery();

            if(queryResult.next())
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
        boolean result = false;

        try {
            String query = "INSERT INTO patente (num_patente, nome, cognome, data_di_nascita, data_di_emissione, data_di_scadenza, comune_di_nascita, sesso, provincia_di_nascita, " +
                    "autorità_emittente, nazionalità) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (num_patente) DO UPDATE SET " +
                    "nome = EXCLUDED.nome, cognome = EXCLUDED.cognome, comune_di_nascita = EXCLUDED.comune_di_nascita, sesso = EXCLUDED.sesso, provincia_di_nascita = EXCLUDED.provincia_di_nascita, " +
                    "data_di_nascita = EXCLUDED.data_di_nascita, data_di_emissione = EXCLUDED.data_di_emissione, data_di_scadenza = EXCLUDED.data_di_scadenza, autorità_emittente = EXCLUDED.autorità_emittente, nazionalità = EXCLUDED.nazionalità";

            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);

            statement.setString(1, pat.getNumIdentificativo());
            statement.setString(2, pat.getNome());
            statement.setString(3, pat.getCognome());
            statement.setDate(4, pat.getDataNascita());
            statement.setDate(5, pat.getDataEmissione());
            statement.setDate(6, pat.getDataScadenza());
            statement.setString(7, pat.getComuneNascita());
            statement.setString(8, pat.getSesso());
            statement.setString(9, pat.getProvNascita());
            statement.setString(10, pat.getEntitaRilascio());
            statement.setString(11, pat.getCittadinanza());

            if(statement.executeUpdate()>0)
                result= true;

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
            String query = "DELETE FROM patente WHERE num_patente = ?";
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);

            statement.setString(1, pat.getNumIdentificativo());
            if (statement.executeUpdate()>0)
                result = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
