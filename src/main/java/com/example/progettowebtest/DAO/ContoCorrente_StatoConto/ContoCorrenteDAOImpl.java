package com.example.progettowebtest.DAO.ContoCorrente_StatoConto;

import com.example.progettowebtest.ClassiRequest.IdentificativiUtente;
import com.example.progettowebtest.Connection.DbConn;
import com.example.progettowebtest.DAO.Indirizzo.IndirizzoDAO;
import com.example.progettowebtest.DAO.Indirizzo.IndirizzoDAOImpl;
import com.example.progettowebtest.DAO.StatoDAO;
import com.example.progettowebtest.DAO.StatoDAOImpl;
import com.example.progettowebtest.DAO.Utente_Documenti.UtenteDAO;
import com.example.progettowebtest.DAO.Utente_Documenti.UtenteDAOImpl;
import com.example.progettowebtest.Model.ContoCorrente.ContoCorrente;
import com.example.progettowebtest.Model.Indirizzo.Indirizzo;
import com.example.progettowebtest.Model.Stato;
import com.example.progettowebtest.Model.Utente_Documenti.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ContoCorrenteDAOImpl implements ContoCorrenteDAO{
    private static ContoCorrenteDAOImpl instance;
    private UtenteDAO utenteDAO= UtenteDAOImpl.getInstance();
    private IndirizzoDAO indirizzoDAO= IndirizzoDAOImpl.getInstance();
    private StatoDAO statoDAO= StatoDAOImpl.getInstance();
    private RelStatoContoDAO relStatoContoDAO= RelStatoContoDAOImpl.getInstance();

    private ContoCorrenteDAOImpl() {};
    public static ContoCorrenteDAOImpl getInstance() {
        if(instance==null)
            instance= new ContoCorrenteDAOImpl();
        return instance;
    }


    @Override
    public Vector<ContoCorrente> doRetriveAll() {
        return null;
    }

    @Override
    public ContoCorrente doRetriveByKey(String numConto) {
        ContoCorrente result= null;
        Utente intestatario= null;
        Indirizzo indFatturazione= null;
        Stato state= null;

        String query= "select * from conto_corrente where num_cc= ?";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            statement.setString(1, numConto);

            ResultSet queryResult= statement.executeQuery();

            if(!queryResult.wasNull()) {
                intestatario= utenteDAO.doRetriveByKey(queryResult.getString("cf"), IdentificativiUtente.CF);
                indFatturazione= indirizzoDAO.doRetriveByKey(queryResult.getString("nome_via_fatturazione"), queryResult.getString("num_civico_fatturazione"),
                        queryResult.getInt("id_comune_fatturazione"), queryResult.getInt("id_via_fatturazione"));
                state= relStatoContoDAO.doRetriveActualState(queryResult.getString("num_cc"));
                result= new ContoCorrente(queryResult.getString("num_cc"), queryResult.getString("iban"), queryResult.getString("pin_sicurezza"), queryResult.getDate("data_apertura").toString(),queryResult.getDouble("limite_conto"), queryResult.getDouble("saldo"), queryResult.getInt("tasso_interesse"), queryResult.getInt("tariffa_annuale"), state, indFatturazione, intestatario);
                //aggiunta dei proxy
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(ContoCorrente contoCorr) {
        boolean result= false;
        String query= "INSERT INTO conto_corrente (num_cc, iban, pin_sicurezza, data_apertura, limite_conto, saldo, tasso_interesse, tariffa_annuale, nome_via_fatturazione, " +
                "num_civico_fatturazione, cf, id_comune_fatturazione, id_via_fatturazione) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (num_cc) DO UPDATE SET" +
                "limite_conto= EXCLUDED.limite"+
                "                    \"ON CONFLICT (cf) DO UPDATE SET nome=EXCLUDED.nome, cognome=EXCLUDED.cognome, cittadinanza=EXCLUDED.cittadinanza, comune_di_nascita=EXCLUDED.comune_di_nascita, sesso=EXCLUDED.sesso, \" +\n" +
                "                    \"provincia_di_nascita=EXCLUDED.provincia_di_nascita, num_telefono=EXCLUDED.num_telefono, data_di_nascita=EXCLUDED.data_di_nascita, email=EXCLUDED.email, password=EXCLUDED.password, \" +\n" +
                "                    \"num_identificativo_ci=EXCLUDED.num_identificativo_ci, num_patente=EXCLUDED.num_patente, num_passaporto=EXCLUDED.num_passaporto, nome_via_domicilio=EXCLUDED.nome_via_domicilio, \" +\n" +
                "                    \"num_civico_domicilio=EXCLUDED.num_civico_domicilio, nome_via_residenza=EXCLUDED.nome_via_residenza, num_civico_residenza=EXCLUDED.num_civico_residenza, id_comune_residenza=EXCLUDED.id_comune_residenza, \" +\n" +
                "                    \"id_comune_domicilio=EXCLUDED.id_comune_domicilio, id_via_residenza=EXCLUDED.id_via_residenza, id_via_domicilio=EXCLUDED.id_via_domicilio, occupazione=EXCLUDED.occupazione, reddito_annuo=EXCLUDED.reddito_annuo ";
        return false;
    }

    @Override
    public boolean delete(ContoCorrente contoCorr) {
        return false;
    }
}
