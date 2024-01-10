package com.example.progettowebtest.DAO.Utente_Documenti;

import com.example.progettowebtest.Connection.DbConn;
import com.example.progettowebtest.Connection.DbConnection;
import com.example.progettowebtest.Model.Passaporto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class PassaportoDAOImpl implements PassaportoDAO{
    private static PassaportoDAOImpl instance;
    private PassaportoDAOImpl() {}
    public static PassaportoDAOImpl getInstance() {
        if(instance==null)
            instance= new PassaportoDAOImpl();
        return instance;
    }

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
            String query = "select * from passaporto where num_passaporto= "+ numIdentificativo;
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);
            ResultSet queryResult = statement.executeQuery();

            if(!queryResult.wasNull())
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
        boolean result = true;

        try {
            String query = "INSERT INTO passaporto (nome, cognome, nazionalità, comune_di_nascita, sesso, provincia_di_nascita, data_di_nascita, num_passaporto, " +
                    "data_di_emissione, data_di_scadenza, comune_di_rilascio) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (num_passaporto) DO UPDATE SET " +
                    "nome = EXCLUDED.nome, cognome = EXCLUDED.cognome, nazionalità = EXCLUDED.nazionalità, comune_di_nascita = EXCLUDED.comune_di_nascita, sesso = EXCLUDED.sesso, " +
                    "provincia_di_nascita = EXCLUDED.provincia_di_nascita, data_di_nascita = EXCLUDED.data_di_nascita, data_di_emissione = EXCLUDED.data_di_emissione, " +
                    "data_di_scadenza = EXCLUDED.data_di_scadenza, comune_di_rilascio = EXCLUDED.comune_di_rilascio";
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);

            statement.setString(1, passa.getNome());
            statement.setString(2, passa.getCognome());
            statement.setString(3, passa.getCittadinanza());
            statement.setString(4, passa.getComuneNascita());
            statement.setString(5, passa.getSesso());
            statement.setString(6, passa.getProvNascita());
            statement.setDate(7, passa.getDataNascita());
            statement.setString(8, passa.getNumIdentificativo());
            statement.setDate(9, passa.getDataEmissione());
            statement.setDate(10, passa.getDataScadenza());
            statement.setString(11, passa.getEntitaRilascio());

            statement.executeUpdate();

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
            String query = "DELETE FROM passaporto WHERE num_passaporto = "+ passa.getNumIdentificativo();
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);

            if (statement.executeUpdate()>0)
                result = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
