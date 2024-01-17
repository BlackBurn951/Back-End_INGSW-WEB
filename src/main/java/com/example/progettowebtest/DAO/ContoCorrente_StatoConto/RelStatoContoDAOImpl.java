package com.example.progettowebtest.DAO.ContoCorrente_StatoConto;

import com.example.progettowebtest.Connection.DbConn;
import com.example.progettowebtest.DAO.MagnusDAO;
import com.example.progettowebtest.DAO.StatoDAO;
import com.example.progettowebtest.DAO.StatoDAOImpl;
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
    public boolean saveOrUpdate(RelStatoConto rel) {
        boolean result= false;
        String query= "INSERT INTO rel_stato_conto (data_inizio_stato, data_fine_stato, id_stato, num_cc) " +
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
            statement.setString(4, rel.getConto().getNumCC());

            statement.executeUpdate();

            result= true;

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean delete(RelStatoConto rel) {
        return false;
    }
}
