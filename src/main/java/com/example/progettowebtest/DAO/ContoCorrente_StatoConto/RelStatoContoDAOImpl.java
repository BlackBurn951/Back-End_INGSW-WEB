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
import java.sql.Types;
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
        RelStatoConto rel= null;

        String query=  "select * from rel_stato_conto where num_cc= ?";;


        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            statement.setString(1, numCC);

            ResultSet queryResult= statement.executeQuery();

            while(queryResult.next()) {
                rel= new RelStatoConto(queryResult.getDate("data_inizio_stato").toString(), statoDAO.doRetriveByKey(queryResult.getInt("id_stato")),
                        contoCorrenteDAO.doRetriveByKey(queryResult.getString("num_cc")));
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

            if(!queryResult.wasNull())
                result= statoDAO.doRetriveByKey(queryResult.getInt("id_stato"));

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(RelStatoConto rel) {
        boolean result= false;
        String query= "INSERT INTO rel_stato_conto (data_inizio_stato, data_fine_stato, id_stato, num_cc) " +
                "VALUES (?, ?, ?, ?) ON CONFLICT (id_stato,num_cc) DO UPDATE SET " +
                "data_inizio_stato=EXCLUDED.data_inizio_stato, data_fine_stato=EXCLUDED.data_fine_stato, id_stato=EXCLUDED.id_stato, num_cc=EXCLUDED.num_cc";

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
