package com.example.progettowebtest.DAO;

import com.example.progettowebtest.Connection.DbConnection;
import com.example.progettowebtest.Model.ColonneDatiComune;
import com.example.progettowebtest.Model.DatiComune;
import com.example.progettowebtest.Model.Indirizzo;
import com.example.progettowebtest.Model.TipoVia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class IndirizzoDAOImpl implements IndirizzoDAO{
    @Override
    public Vector<Indirizzo> doRetriveAll() {
        return null;
    }

    @Override
    public Indirizzo doRetriveByKey(String nomeVia, String numCivico, String comune, String tipologia) {
        Indirizzo result= null;

        DatiComuneDAOImpl comuneDAO= new DatiComuneDAOImpl();
        TipoViaDAOImpl viaDAO= new TipoViaDAOImpl();
        DatiComune comuneIns= comuneDAO.doRetriveByAttribute(comune, ColonneDatiComune.NOME_COMUNE);;
        TipoVia tipo= viaDAO.doRetriveByAttribute(tipologia);;

        try{
            String query= "select * from indirizzo where nome_via= "+ nomeVia+" and num_civico= "+ numCivico+ " and id_comune= "+ comuneIns.getIdComune()+" and id_via= "+tipo.getIdVia();
            PreparedStatement statement= DbConnection.getInstance().prepareStatement(query);
            ResultSet queryResult= statement.executeQuery();

            result= new Indirizzo(tipo, queryResult.getString("nome_via"), queryResult.getString("num_civico"), comuneIns);

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(Indirizzo ind) {
        boolean result= true;

        try {
            //Controllo che l'indirizzo sia esistente per fare l'update
            String query= "insert into indirizzo(nome_via, num_civico, id_comune, id_via) values(?,?,?,?)";
            PreparedStatement statement= DbConnection.getInstance().prepareStatement(query);

            statement.setString(1, ind.getNomeVia());
            statement.setString(2, ind.getNumCivico());
            statement.setInt(3, ind.getComune().getIdComune());
            statement.setInt(4, ind.getTipologiaVia().getIdVia());

            statement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean delete(Indirizzo ind) {
        return false;
    }
}
