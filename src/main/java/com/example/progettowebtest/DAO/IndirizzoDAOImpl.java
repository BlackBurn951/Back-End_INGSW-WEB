package com.example.progettowebtest.DAO;

import com.example.progettowebtest.Connection.DbConnection;
import com.example.progettowebtest.Model.ColonneDatiComune;
import com.example.progettowebtest.Model.DatiComune;
import com.example.progettowebtest.Model.Indirizzo;
import com.example.progettowebtest.Model.TipoVia;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class IndirizzoDAOImpl implements IndirizzoDAO{
    private static IndirizzoDAOImpl instance;
    private DatiComuneDAO comuneDAO= DatiComuneDAOImpl.getInstance();
    private TipoViaDAO tipoViaDAO= TipoViaDAOImpl.getInstance();


    private static DataSource dataSource;

    public IndirizzoDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public static synchronized IndirizzoDAOImpl getInstance(DataSource dataSource) {
        if (instance == null)
            instance = new IndirizzoDAOImpl(dataSource);
        return instance;
    }

    @Override
    public Vector<Indirizzo> doRetriveAll() {
        Vector<Indirizzo> resultList = new Vector<>();
        int idComune, idTipoVia;

        try {
            String query = "SELECT * FROM indirizzo";
            PreparedStatement statement = dataSource.getConnection().prepareStatement(query);
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
                String query = "select * from indirizzo where nome_via= " + nomeVia + " and num_civico= " + numCivico + " and id_comune= " + comuneIns.getIdComune() + " and id_via= " + tipo.getIdVia();
                PreparedStatement statement = DbConnection.getInstance().prepareStatement(query);
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
            PreparedStatement statement= DbConnection.getInstance().prepareStatement(query);

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
            PreparedStatement statement = DbConnection.getInstance().prepareStatement(query);

            if (statement.executeUpdate()>0)
                result = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
