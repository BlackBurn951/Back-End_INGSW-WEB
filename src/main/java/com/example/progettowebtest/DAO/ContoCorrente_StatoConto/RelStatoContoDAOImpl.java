package com.example.progettowebtest.DAO.ContoCorrente_StatoConto;

import com.example.progettowebtest.Connection.DbConn;
import com.example.progettowebtest.DAO.MagnusDAO;
import com.example.progettowebtest.Model.ContoCorrente.RelStatoConto;
import com.example.progettowebtest.Model.Stato;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Vector;

public class RelStatoContoDAOImpl implements RelStatoContoDAO{
    public RelStatoContoDAOImpl() {}


    @Override
    public Vector<RelStatoConto> doRetriveAll() {
        return null;
    }

    @Override
    public RelStatoConto doRetriveByKey(int id) {
        return null;
    }

    @Override
    public Vector<RelStatoConto> doRetriveByAttribute(String numCC) {
        Vector<RelStatoConto> result= new Vector<>();
        RelStatoConto rel= null;

        String query=  "select * from rel_stato_conto where num_cc= ?";;


        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            statement.setString(1, numCC);

            ResultSet queryResult= statement.executeQuery();

            while(queryResult.next()) {
                rel= new RelStatoConto(queryResult.getDate("data_inizio_stato").toString(), MagnusDAO.getInstance().getStatoDAO().doRetriveByKey(queryResult.getInt("id_stato")),
                        MagnusDAO.getInstance().getContoCorrenteDAO().doRetriveByKey(queryResult.getString("num_cc")));
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
    public Stato doRetriveActualState(String numCC) {
        Stato result= null;
        String query=  "select * from rel_stato_conto where num_cc= ? and data_fine_stato IS NULL";;


        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            statement.setString(1, numCC);

            ResultSet queryResult= statement.executeQuery();

            if(queryResult.next())
                result= MagnusDAO.getInstance().getStatoDAO().doRetriveByKey(queryResult.getInt("id_stato"));

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public RelStatoConto doRetriveActualRel(String numCC) {
        RelStatoConto result= null;
        String query= "select * from rel_stato_conto where num_cc= ? and data_fine_stato IS NULL";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            statement.setString(1, numCC);

            ResultSet queryResult= statement.executeQuery();

            if(queryResult.next()) {
                result = new RelStatoConto(queryResult.getDate("data_inizio_stato").toString(), MagnusDAO.getInstance().getStatoDAO().doRetriveByKey(queryResult.getInt("id_stato")),
                        MagnusDAO.getInstance().getContoCorrenteDAO().doRetriveByKey(numCC));
                result.setId(queryResult.getInt("id_rel"));
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(RelStatoConto rel) {
        String query= "";

        if(rel.getId()>-1)
            query= "INSERT INTO rel_stato_conto (id_rel, data_inizio_stato, data_fine_stato, id_stato, num_cc) " +
                "VALUES (?, ?, ?, ?, ?) ON CONFLICT (id_rel) DO UPDATE SET " +
                    "data_inizio_stato=EXCLUDED.data_inizio_stato, data_fine_stato=EXCLUDED.data_fine_stato, id_stato=EXCLUDED.id_stato, num_cc=EXCLUDED.num_cc";
        else
            query= "INSERT INTO rel_stato_conto (data_inizio_stato, data_fine_stato, id_stato, num_cc) " +
                    "VALUES (?, ?, ?, ?)";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);

            if(rel.getId()>-1) {
                statement.setInt(1, rel.getId());
                statement.setDate(2, rel.getDataInizioStato());
                statement.setDate(3, rel.getDataFineStato());
                statement.setInt(4, rel.getStato().getIdStato());
                statement.setString(5, rel.getConto().getNumCC());
            }
            else {
                statement.setDate(1, rel.getDataInizioStato());
                statement.setNull(2, Types.NULL);
                statement.setInt(3, rel.getStato().getIdStato());
                statement.setString(4, rel.getConto().getNumCC());
            }

            if(statement.executeUpdate()>0)
                return true;

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(String numCC) {
        String query = "DELETE FROM rel_stato_conto WHERE num_cc = ?";

        try {
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);
            statement.setString(1, numCC);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
