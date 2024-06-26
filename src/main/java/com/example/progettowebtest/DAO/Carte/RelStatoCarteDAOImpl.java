package com.example.progettowebtest.DAO.Carte;

import com.example.progettowebtest.Connection.DbConn;
import com.example.progettowebtest.DAO.MagnusDAO;
import com.example.progettowebtest.Model.Carte.RelStatoCarta;
import com.example.progettowebtest.Model.Carte.TipiCarte;
import com.example.progettowebtest.Model.Stato;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Vector;

public class RelStatoCarteDAOImpl implements RelStatoCarteDAO{
    @Override
    public Vector<RelStatoCarta> doRetriveAll() {
        return null;
    }

    @Override
    public RelStatoCarta doRetriveByKey(int id) {
        return null;
    }

    @Override
    public Vector<RelStatoCarta> doRetriveByAttribute(String numCarta, TipiCarte tipo) {
        Vector<RelStatoCarta> result= new Vector<>();
        RelStatoCarta rel= null;
        String query= "";

        if(tipo==TipiCarte.CREDITO)
            query= "select * from rel_stato_carta_credito where num_carta= ?";
        else
            query= "select * from rel_stato_carta_debito where num_carta= ?";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            statement.setString(1, numCarta);

            ResultSet queryResult= statement.executeQuery();
            while(queryResult.next()) {
                rel= new RelStatoCarta(queryResult.getDate("data_inizio_stato").toString(),
                        MagnusDAO.getInstance().getStatoDAO().doRetriveByKey(queryResult.getInt("id_stato")),
                        MagnusDAO.getInstance().getCarteDAO().doRetriveByKey(queryResult.getString("num_cc"), tipo, false));
                rel.setId(queryResult.getInt("id_rel"));
                rel.setDataFineStato(queryResult.getDate("data_fine_stato").toString());
                result.add(rel);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public RelStatoCarta doRetriveActualRel(String numCarta, TipiCarte tipo) {
        RelStatoCarta result= null;
        String query= "";

        if(tipo==TipiCarte.CREDITO)
            query= "select * from rel_stato_carta_credito where num_carta= ? and data_fine_stato IS NULL";
        else
            query= "select * from rel_stato_carta_debito where num_carta= ? and data_fine_stato IS NULL";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            statement.setString(1, numCarta);

            ResultSet queryResult= statement.executeQuery();

            if(queryResult.next()) {
                result = new RelStatoCarta(queryResult.getDate("data_inizio_stato").toString(), MagnusDAO.getInstance().getStatoDAO().doRetriveByKey(queryResult.getInt("id_stato")),
                        MagnusDAO.getInstance().getCarteDAO().doRetriveByKey(numCarta, tipo, false));
                result.setId(queryResult.getInt("id_rel"));
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Stato doRetriveActualState(String numCarta, TipiCarte tipo) {
        Stato result= null;
        String query= "";

        if(tipo==TipiCarte.CREDITO)
            query= "select * from rel_stato_carta_credito where num_carta= ? and data_fine_stato IS NULL";
        else
            query= "select * from rel_stato_carta_debito where num_carta= ? and data_fine_stato IS NULL";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            statement.setString(1, numCarta);

            ResultSet queryResult= statement.executeQuery();

            if(queryResult.next())
                result = MagnusDAO.getInstance().getStatoDAO().doRetriveByKey(queryResult.getInt("id_stato"));

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(RelStatoCarta rel, TipiCarte tipo) {
        String query= "";

        if(rel.getId()>-1) {
            if(tipo==TipiCarte.CREDITO)
                query = "INSERT INTO rel_stato_carta_credito (id_rel ,data_inizio_stato, data_fine_stato, num_carta, id_stato) " +
                        "VALUES (?, ?, ?, ?, ?) ON CONFLICT (id_rel) DO UPDATE SET data_inizio_stato=EXCLUDED.data_inizio_stato, data_fine_stato=EXCLUDED.data_fine_stato, id_stato=EXCLUDED.id_stato, num_carta=EXCLUDED.num_carta";
            else
                query = "INSERT INTO rel_stato_carta_debito (id_rel, data_inizio_stato, data_fine_stato, num_carta, id_stato) " +
                        "VALUES (?, ?, ?, ?, ?) ON CONFLICT (id_rel) DO UPDATE SET data_inizio_stato=EXCLUDED.data_inizio_stato, data_fine_stato=EXCLUDED.data_fine_stato, id_stato=EXCLUDED.id_stato, num_carta=EXCLUDED.num_carta";
        }
        else {
            if (tipo == TipiCarte.CREDITO)
                query = "INSERT INTO rel_stato_carta_credito (data_inizio_stato, data_fine_stato, num_carta, id_stato) " +
                        "VALUES (?, ?, ?, ?)";
            else
                query = "INSERT INTO rel_stato_carta_debito (data_inizio_stato, data_fine_stato, num_carta, id_stato) " +
                        "VALUES (?, ?, ?, ?)";
        }
        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);

            if(rel.getId()>-1) {
                statement.setInt(1, rel.getId());
                statement.setDate(2, rel.getDataInizioStato());
                statement.setDate(3, rel.getDataFineStato());
                statement.setString(4, rel.getCarta().getNumCarta());
                statement.setInt(5, rel.getStato().getIdStato());
            }
            else {
                statement.setDate(1, rel.getDataInizioStato());
                statement.setNull(2, Types.NULL);
                statement.setString(3, rel.getCarta().getNumCarta());
                statement.setInt(4, rel.getStato().getIdStato());
            }

            if(statement.executeUpdate()>0)
                return true;

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(String numCarta, TipiCarte tipo) {
        String query = "";

        if (tipo == TipiCarte.CREDITO)
            query = "DELETE FROM rel_stato_carta_credito WHERE num_carta = ?";
        else
            query = "DELETE FROM rel_stato_carta_debito WHERE num_carta = ?";

        try {
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);
            statement.setString(1, numCarta);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
