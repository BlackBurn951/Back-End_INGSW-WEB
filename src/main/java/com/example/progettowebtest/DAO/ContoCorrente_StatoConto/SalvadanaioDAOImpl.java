package com.example.progettowebtest.DAO.ContoCorrente_StatoConto;

import com.example.progettowebtest.Connection.DbConn;
import com.example.progettowebtest.DAO.Indirizzo.DatiComuneDAO;
import com.example.progettowebtest.DAO.Indirizzo.DatiComuneDAOImpl;
import com.example.progettowebtest.Model.ContoCorrente.ContoCorrente;
import com.example.progettowebtest.Model.ContoCorrente.Salvadanaio;
import com.example.progettowebtest.Model.Indirizzo.TipoVia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class SalvadanaioDAOImpl implements SalvadanaioDAO {

    private ContoCorrenteDAO contoDAO = ContoCorrenteDAOImpl.getInstance();


    private static SalvadanaioDAOImpl instance;


    private SalvadanaioDAOImpl() {
    }


    public static SalvadanaioDAOImpl getInstance() {
        if (instance == null)
            instance = new SalvadanaioDAOImpl();
        return instance;
    }

    @Override
    public Vector<Salvadanaio> doRetriveAll() {
        Vector<Salvadanaio> salvadanai = new Vector<>();

        try {
            String query = "SELECT * FROM salvadanaio";
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);
            ResultSet queryResult = statement.executeQuery();

            while (queryResult.next()) {
                ContoCorrente contoCorrente = contoDAO.doRetriveByKey(queryResult.getString("num_cc"));
                Salvadanaio salvadanaio = new Salvadanaio(
                        queryResult.getInt("id_salvadanaio"),
                        queryResult.getDouble("saldo_attuale"),
                        queryResult.getDouble("obiettivo"),
                        queryResult.getString("nome_obiettivo"),
                        contoCorrente
                );
                salvadanai.add(salvadanaio);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return salvadanai;
    }


    @Override
    public Salvadanaio doRetriveByKey(int id) {
        Salvadanaio result = null;


        try {
            String query = "select * from salvadanaio where id_salvadanaio= ?";

            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);
            ResultSet queryResult = statement.executeQuery();

            if (!queryResult.wasNull()) {
                ContoCorrente contoCorrente = contoDAO.doRetriveByKey(queryResult.getString("num_cc"));
                result = new Salvadanaio(queryResult.getInt("id_salvadanaio"),
                        queryResult.getDouble("saldo_attuale"),
                        queryResult.getDouble("obiettivo"),
                        queryResult.getString("nome_obiettivo"),
                        contoCorrente);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public boolean saveOrUpdate(Salvadanaio salva) {
        try {
            String query = "INSERT INTO salvadanaio (id_salvadanaio, saldo_attuale, obiettivo, nome_obiettivo, num_cc) VALUES (?, ?, ?, ?, ?) " +
                    "ON CONFLICT (id_salvadanaio) DO UPDATE SET saldo_attuale = EXCLUDED.saldo_attuale, obiettivo = EXCLUDED.obiettivo, nome_obiettivo = EXCLUDED.nome_obiettivo, num_cc = EXCLUDED.num_cc";
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);
            statement.setInt(1, salva.getIdSalvadanaio());
            statement.setDouble(2, salva.getSaldoAttuale());
            statement.setDouble(3, salva.getObiettivo());
            statement.setString(4, salva.getNomeObiettivo());
            statement.setString(5, salva.getContoRiferimento().getNumCC());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean delete(Salvadanaio salva) {
        try {
            String query = "DELETE FROM salvadanaio WHERE id_salvadanaio = ?";
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);
            statement.setInt(1, salva.getIdSalvadanaio());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

