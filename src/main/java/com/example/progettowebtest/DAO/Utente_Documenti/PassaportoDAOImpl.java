package com.example.progettowebtest.DAO.Utente_Documenti;

import com.example.progettowebtest.Connection.DbConn;
import com.example.progettowebtest.Model.Utente_Documenti.Passaporto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class PassaportoDAOImpl implements PassaportoDAO{
    public PassaportoDAOImpl() {}

    @Override
    public Vector<Passaporto> doRetriveAll() {
        Vector<Passaporto> resultList = new Vector<>();

        try {
            String query = "SELECT * FROM passaporto";
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);
            ResultSet queryResult = statement.executeQuery();

            while (queryResult.next()) {
                Passaporto passaporto = new Passaporto(queryResult.getString("nome"), queryResult.getString("cognome"),
                        queryResult.getString("nazionalità"), queryResult.getString("comune_di_nascita"), queryResult.getString("sesso"),
                        queryResult.getString("provincia_di_nascita"), queryResult.getDate("data_di_nascita").toString(),
                        queryResult.getString("num_passaporto"), queryResult.getDate("data_di_emissione").toString(),
                        queryResult.getDate("data_di_scadenza").toString(), queryResult.getString("comune_di_rilascio"));
                resultList.add(passaporto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public Passaporto doRetriveByKey(String numIdentificativo) {
        Passaporto result = null;

        try{
            String query = "select * from passaporto where num_passaporto= ?";
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);

            statement.setString(1, numIdentificativo);
            ResultSet queryResult = statement.executeQuery();

            if(queryResult.next())
                result = new Passaporto(queryResult.getString("nome"), queryResult.getString("cognome"), queryResult.getString("nazionalità"),
                        queryResult.getString("comune_di_nascita"), queryResult.getString("sesso"), queryResult.getString("provincia_di_nascita"),
                        queryResult.getDate("data_di_nascita").toString(), queryResult.getString("num_passaporto"), queryResult.getDate("data_di_emissione").toString(),
                        queryResult.getDate("data_di_scadenza").toString(), queryResult.getString("comune_di_rilascio"));

        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(Passaporto passa) {
        boolean result = false;

        try {
            String query = "INSERT INTO passaporto (num_passaporto, nome, cognome, data_di_nascita, data_di_emissione, data_di_scadenza, comune_di_nascita, " +
                    "sesso, provincia_di_nascita, comune_di_rilascio, nazionalità) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (num_passaporto) DO UPDATE SET " +
                    "nome = EXCLUDED.nome, cognome = EXCLUDED.cognome, nazionalità = EXCLUDED.nazionalità, comune_di_nascita = EXCLUDED.comune_di_nascita, sesso = EXCLUDED.sesso, " +
                    "provincia_di_nascita = EXCLUDED.provincia_di_nascita, data_di_nascita = EXCLUDED.data_di_nascita, data_di_emissione = EXCLUDED.data_di_emissione, " +
                    "data_di_scadenza = EXCLUDED.data_di_scadenza, comune_di_rilascio = EXCLUDED.comune_di_rilascio";
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);

            statement.setString(1, passa.getNumIdentificativo());
            statement.setString(2, passa.getNome());
            statement.setString(3, passa.getCognome());
            statement.setDate(4, passa.getDataNascita());
            statement.setDate(5, passa.getDataEmissione());
            statement.setDate(6, passa.getDataScadenza());
            statement.setString(7, passa.getComuneNascita());
            statement.setString(8, passa.getSesso());
            statement.setString(9, passa.getProvNascita());
            statement.setString(10, passa.getEntitaRilascio());
            statement.setString(11, passa.getCittadinanza());

            if(statement.executeUpdate()>0)
                result= true;

        } catch (SQLException e) {
            e.printStackTrace();
            result= false;
        }
        return result;
    }

    @Override
    public boolean delete(Passaporto passa) {
        boolean result = false;

        try {
            String query = "DELETE FROM passaporto WHERE num_passaporto = ?";
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);

            statement.setString(1, passa.getNumIdentificativo());
            if (statement.executeUpdate()>0)
                result = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
