package com.example.progettowebtest.DAO.Utente_Documenti;

import com.example.progettowebtest.Connection.DbConn;
import com.example.progettowebtest.Model.Utente_Documenti.CartaIdentita;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class CartaIdentitaDAOImpl implements CartaIdentitaDAO{
    private static CartaIdentitaDAOImpl instance;
    private CartaIdentitaDAOImpl() {}
    public static CartaIdentitaDAOImpl getInstance() {
        if(instance==null)
            instance= new CartaIdentitaDAOImpl();
        return instance;
    }

    @Override
    public Vector<CartaIdentita> doRetriveAll() {
        Vector<CartaIdentita> resultList = new Vector<>();

        try {
            String query = "SELECT * FROM carta_di_identità";
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);
            ResultSet queryResult = statement.executeQuery();

            while (queryResult.next()) {
                CartaIdentita cartaIdentita = new CartaIdentita(queryResult.getString("nome"), queryResult.getString("cognome"), queryResult.getString("nazionalità"),
                        queryResult.getString("comune_di_nascita"), queryResult.getString("sesso"), queryResult.getString("provincia_di_nascita"),
                        queryResult.getDate("data_di_nascita").toString(), queryResult.getString("num_identificativo"), queryResult.getDate("data_di_emissione").toString(),
                        queryResult.getDate("data_di_scadenza").toString(), queryResult.getString("comune_di_rilascio"));
                resultList.add(cartaIdentita);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public CartaIdentita doRetriveByKey(String numIdentificativo) {
        CartaIdentita result = null;

        try{
            String query = "select * from carta_di_identità where num_identificativo= ?";
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);

            statement.setString(1,numIdentificativo);
            ResultSet queryResult = statement.executeQuery();

            if(!queryResult.wasNull())
                result = new CartaIdentita(queryResult.getString("nome"), queryResult.getString("cognome"), queryResult.getString("nazionalità"),
                        queryResult.getString("comune_di_nascita"), queryResult.getString("sesso"), queryResult.getString("provincia_di_nascita"),
                        queryResult.getDate("data_di_nascita").toString(), queryResult.getString("num_identificativo"), queryResult.getDate("data_di_emissione").toString(),
                        queryResult.getDate("data_di_scadenza").toString(), queryResult.getString("comune_di_rilascio"));

        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(CartaIdentita cd) {
        boolean result = true;

        try {
            String query = "INSERT INTO carta_di_identità (num_identificativo, nome, cognome, nazionalità, comune_di_nascita, sesso, provincia_di_nascita, data_di_nascita, data_di_emissione, data_di_scadenza, comune_di_rilascio) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (num_identificativo) DO UPDATE SET nome = EXCLUDED.nome, cognome = EXCLUDED.cognome, nazionalità = EXCLUDED.nazionalità, " +
                    "comune_di_nascita = EXCLUDED.comune_di_nascita, sesso = EXCLUDED.sesso, provincia_di_nascita = EXCLUDED.provincia_di_nascita, data_di_nascita = EXCLUDED.data_di_nascita, " +
                    "data_di_emissione = EXCLUDED.data_di_emissione, data_di_scadenza = EXCLUDED.data_di_scadenza, comune_di_rilascio = EXCLUDED.comune_di_rilascio";
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);

            statement.setString(1, cd.getNumIdentificativo());
            statement.setString(2, cd.getNome());
            statement.setString(3, cd.getCognome());
            statement.setString(4, cd.getCittadinanza());
            statement.setString(5, cd.getComuneNascita());
            statement.setString(6, cd.getSesso());
            statement.setString(7, cd.getProvNascita());
            statement.setDate(8, cd.getDataNascita());
            statement.setDate(9, cd.getDataEmissione());
            statement.setDate(10, cd.getDataScadenza());
            statement.setString(11, cd.getEntitaRilascio());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            result= false;
        }
        return result;
    }

    @Override
    public boolean delete(CartaIdentita cd) {
        boolean result= false;

        try {
            String query = "DELETE FROM carta_di_identità WHERE num_identificativo = "+cd.getNumIdentificativo() ;
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);

            if(statement.executeUpdate()>0)
                result= true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


}
