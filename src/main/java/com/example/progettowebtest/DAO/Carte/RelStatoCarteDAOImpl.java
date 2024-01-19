package com.example.progettowebtest.DAO.Carte;

import com.example.progettowebtest.Connection.DbConn;
import com.example.progettowebtest.DAO.MagnusDAO;
import com.example.progettowebtest.Model.Carte.RelStatoCarta;
import com.example.progettowebtest.Model.ContoCorrente.RelStatoConto;
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
    public Vector<RelStatoCarta> doRetriveByAttribute(String numCarta, boolean tipo) {
        Vector<RelStatoCarta> result= new Vector<>();
        RelStatoCarta rel= null;
        String query= "";

        if(tipo)
            query= "select * from rel_stato_carta_credito where num_carta= ?";
        else
            query= "select * from rel_stato_carta_debito where num_carta= ?";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            statement.setString(1, numCarta);

            ResultSet queryResult= statement.executeQuery();

            while(queryResult.next())
                result.add(new RelStatoCarta(queryResult.getInt("id_rel"), queryResult.getDate("data_inizio_stato").toString(), queryResult.getDate("data_fine_stato").toString(),
                        MagnusDAO.getInstance().getStatoDAO().doRetriveByKey(queryResult.getInt("id_stato")),
                        MagnusDAO.getInstance().getCarteDAO().doRetriveByKey(queryResult.getString("num_cc"), tipo, false)));

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Stato doRetriveActualState(String numCarta, boolean tipo) {
        Stato result= null;
        String query= "";

        if(tipo)
            query= "select * from rel_stato_carta_credito where num_carta= ? and data_fine_stato IS NULL";
        else
            query= "select * from rel_stato_carta_credito where num_carta= ? and data_fine_stato IS NULL";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            statement.setString(1, numCarta);

            ResultSet queryResult= statement.executeQuery();

            if(queryResult.next())
                result= MagnusDAO.getInstance().getStatoDAO().doRetriveByKey(queryResult.getInt("id_stato"));

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(RelStatoCarta rel, boolean tipo) {
        String query= "";
        if(tipo)
            query = "INSERT INTO rel_stato_carta_credito (data_inizio_stato, data_fine_stato, num_carta, id_stato) " +
                "VALUES (?, ?, ?, ?)";
        else
            query = "INSERT INTO rel_stato_carta_debito (data_inizio_stato, data_fine_stato, num_carta, id_stato) " +
                    "VALUES (?, ?, ?, ?)";
        //ON CONFLICT (id_rel) DO UPDATE SET " +
        //"data_inizio_stato=EXCLUDED.data_inizio_stato, data_fine_stato=EXCLUDED.data_fine_stato, id_stato=EXCLUDED.id_stato, num_cc=EXCLUDED.num_cc"
        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);

            statement.setDate(1, rel.getDataInizioStato());
            if(rel.getDataFineStato()==null)
                statement.setNull(2, Types.NULL);
            else
                statement.setDate(2, rel.getDataFineStato());
            statement.setInt(3, rel.getStato().getIdStato());
            statement.setString(4, rel.getCarta().getNumCarta());

            if(statement.executeUpdate()>0)
                return true;

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(RelStatoCarta rel) {
        return false;
    }
}