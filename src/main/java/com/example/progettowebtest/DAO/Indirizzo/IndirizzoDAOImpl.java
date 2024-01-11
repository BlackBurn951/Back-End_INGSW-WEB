package com.example.progettowebtest.DAO.Indirizzo;

import com.example.progettowebtest.Connection.DbConn;
import com.example.progettowebtest.Model.Indirizzo.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class IndirizzoDAOImpl implements IndirizzoDAO{
    private static IndirizzoDAOImpl instance;
    private DatiComuneDAO comuneDAO= DatiComuneDAOImpl.getInstance();
    private TipoViaDAO tipoViaDAO= TipoViaDAOImpl.getInstance();


    public IndirizzoDAOImpl(){}


    public static IndirizzoDAOImpl getInstance() {
        if (instance == null)
            instance = new IndirizzoDAOImpl();
        return instance;
    }

    @Override
    public Vector<Indirizzo> doRetriveAll() {
        Vector<Indirizzo> resultList = new Vector<>();
        int idComune, idTipoVia;

        try {
            String query = "SELECT * FROM indirizzo";
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);
            ResultSet queryResult = statement.executeQuery();

            while (queryResult.next()) {
                idComune = queryResult.getInt("id_comune");
                idTipoVia = queryResult.getInt("id_via");

                DatiComune comuneIns = comuneDAO.doRetriveByKey(idComune);
                TipoVia tipo = tipoViaDAO.doRetriveByKey(idTipoVia);

                Indirizzo indirizzo = new Indirizzo(tipo, queryResult.getString("nome_via"), queryResult.getString("num_civico"), comuneIns);
                resultList.add(indirizzo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public Indirizzo doRetriveByKey(String nomeVia, String numCivico, int comune, int tipologia) {
        Indirizzo result= null;

        DatiComune comuneIns= comuneDAO.doRetriveByKey(comune);
        TipoVia tipo= tipoViaDAO.doRetriveByKey(tipologia);

        if(comuneIns!=null && tipo!=null) {
            try {
                String query = "select * from indirizzo where nome_via= ? and num_civico=? and id_comune= ? and id_via= ?";
                PreparedStatement statement = DbConn.getConnection().prepareStatement(query);

                statement.setString(1, nomeVia);
                statement.setString(2, numCivico);
                statement.setInt(3, comuneIns.getIdComune());
                statement.setInt(4, tipo.getIdVia());

                ResultSet queryResult = statement.executeQuery();

                if (!queryResult.wasNull())
                    result = new Indirizzo(tipo, queryResult.getString("nome_via"), queryResult.getString("num_civico"), comuneIns);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(Indirizzo ind) {
        boolean result= true;

        try {
            String query= "insert into indirizzo(nome_via, num_civico, id_comune, id_via) values(?,?,?,?) ON CONFLICT (nome_via, num_civico, id_comune, id_via) DO NOTHING";
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);

            statement.setString(1, ind.getNomeVia());
            statement.setString(2, ind.getNumCivico());
            statement.setInt(3, ind.getComune().getIdComune());
            statement.setInt(4, ind.getTipologiaVia().getIdVia());

            statement.executeUpdate();

        }catch (SQLException e) {
            e.printStackTrace();
            result= false;
        }
        return result;
    }

    @Override
    public boolean delete(Indirizzo ind) {
        boolean result = false;

        try {
            String query = "DELETE FROM indirizzo WHERE nome_via = "+ind.getNomeVia()+" AND num_civico = "+ind.getNumCivico()+" AND " +
                    "id_comune = "+ind.getComune().getIdComune()+" AND id_via = "+ind.getTipologiaVia().getIdVia();
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);

            if (statement.executeUpdate()>0)
                result = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
