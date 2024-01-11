package com.example.progettowebtest.DAO.ContoCorrente_StatoConto;

import com.example.progettowebtest.Connection.DbConn;
import com.example.progettowebtest.DAO.Carte.CartaCreditoDAO;
import com.example.progettowebtest.DAO.Carte.CartaCreditoDAOImpl;
import com.example.progettowebtest.DAO.Carte.CartaDebitoDAO;
import com.example.progettowebtest.DAO.Carte.CartaDebitoDAOImpl;
import com.example.progettowebtest.DAO.StatoDAO;
import com.example.progettowebtest.DAO.StatoDAOImpl;
import com.example.progettowebtest.Model.Carte.Carta;
import com.example.progettowebtest.Model.Carte.RelStatoCarta;
import com.example.progettowebtest.Model.ContoCorrente.ContoCorrente;
import com.example.progettowebtest.Model.ContoCorrente.RelStatoConto;
import com.example.progettowebtest.Model.Stato;
import com.example.progettowebtest.Model.TabelleCorelateStato;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class RelStatoContoDAOImpl implements RelStatoContoDAO{
    private static RelStatoContoDAOImpl instance;
    private StatoDAO statoDAO= StatoDAOImpl.getInstance();
    private ContoCorrenteDAO contoCorrenteDAO= ContoCorrenteDAOImpl.getInstance();
    private CartaDebitoDAO cartaDebitoDAO= CartaDebitoDAOImpl.getInstance();
    private CartaCreditoDAO cartaCreditoDAO= CartaCreditoDAOImpl.getInstance();

    private RelStatoContoDAOImpl() {}

    public static RelStatoContoDAOImpl getInstance() {
        if(instance==null)
            instance= new RelStatoContoDAOImpl();
        return instance;
    }

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
        String query=  "select * from rel_stato_conto where num_cc= ?";;


        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            statement.setString(1, numCC);

            ResultSet queryResult= statement.executeQuery();

            while(queryResult.next()) {
                    result.add(new RelStatoConto(queryResult.getInt("id_rel"), queryResult.getDate("data_inizio_stato").toString(),
                            queryResult.getDate("data_fine_stato").toString(), statoDAO.doRetriveByKey(queryResult.getInt("id_stato")), contoCorrenteDAO.doRetriveByKey(queryResult.getString("num_cc"))));
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

            if(!queryResult.wasNull())
                result= statoDAO.doRetriveByKey(queryResult.getInt("id_stato"));

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(RelStatoConto rel) {
        return false;
    }

    @Override
    public boolean delete(RelStatoConto rel) {
        return false;
    }
}
