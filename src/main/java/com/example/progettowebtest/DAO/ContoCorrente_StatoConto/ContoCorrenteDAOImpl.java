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
import com.example.progettowebtest.Model.ContoCorrente.RelStatoConto;
import com.example.progettowebtest.Model.Indirizzo.Indirizzo;
import com.example.progettowebtest.Model.Stato;
import com.example.progettowebtest.Model.Utente_Documenti.*;
import com.example.progettowebtest.Model.ValoriStato;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
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
                result= new ContoCorrente(queryResult.getString("num_cc"), queryResult.getString("iban"), queryResult.getString("pin_sicurezza"), queryResult.getDate("data_apertura").toString(),
                        queryResult.getDouble("saldo"), queryResult.getInt("tasso_interesse"), queryResult.getInt("tariffa_annuale"), indFatturazione, intestatario);
                result.setStatoConto(state);
                //aggiunta dei proxy
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(ContoCorrente contoCorr, boolean fristTime) {
        boolean result= false;
        String query= "INSERT INTO conto_corrente (num_cc, iban, pin_sicurezza, data_apertura, saldo, tasso_interesse, tariffa_annuale, nome_via_fatturazione, " +
                "num_civico_fatturazione, cf, id_comune_fatturazione, id_via_fatturazione) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (num_cc) DO UPDATE SET " +
                "saldo=EXCLUDED.saldo, tasso_interesse=EXCLUDED.tasso_interesse, tariffa_annuale=EXCLUDED.tariffa_annuale, nome_via_fatturazione=EXCLUDED.nome_via_fatturazione," +
                "num_civico_fatturazione=EXCLUDED.num_civico_fatturazione, id_comune_fatturazione=EXCLUDED.id_comune_fatturazione, id_via_fatturazione=EXCLUDED.id_via_fatturazione", numConto= "", pin= "", iban= "";

        if(fristTime) {
            Random rng = new Random();
            numConto= generatealphaNum(rng);
            pin = String.valueOf(rng.nextInt(10)) + String.valueOf(rng.nextInt(10)) + String.valueOf(rng.nextInt(10)) + String.valueOf(rng.nextInt(10)) + String.valueOf(rng.nextInt(10));
            iban = "IT" + String.valueOf(rng.nextInt(10)) + String.valueOf(rng.nextInt(10)) + generateAlpha(rng.nextInt(21)) + String.valueOf(rng.nextInt(10)) + String.valueOf(rng.nextInt(10)) + String.valueOf(rng.nextInt(10)) + String.valueOf(rng.nextInt(10))
                            + String.valueOf(rng.nextInt(10)) + String.valueOf(rng.nextInt(10)) + String.valueOf(rng.nextInt(10)) + String.valueOf(rng.nextInt(10)) + String.valueOf(rng.nextInt(10)) + String.valueOf(rng.nextInt(10)) + numConto;
            pin = BCrypt.hashpw(pin, BCrypt.gensalt(5));
        }

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);

            if(fristTime) {
                statement.setString(1, numConto);
                statement.setString(2, iban);
                statement.setString(3, pin);
                statement.setDate(4, contoCorr.getDataApertura());
                statement.setDouble(5, 500.0);
                statement.setInt(6, 3);
                statement.setInt(7, 20);
                statement.setString(8, contoCorr.getIndFatturazione().getNomeVia());
                statement.setString(9, contoCorr.getIndFatturazione().getNumCivico());
                statement.setString(10, contoCorr.getIntestatario().getCodiceFiscale());
                statement.setInt(11, contoCorr.getIndFatturazione().getComune().getIdComune());
                statement.setInt(12, contoCorr.getIndFatturazione().getTipologiaVia().getIdVia());
            }
            else {
                statement.setString(1, contoCorr.getNumCC());
                statement.setString(2, contoCorr.getIban());
                statement.setString(3, contoCorr.getPin());
                statement.setDate(4, contoCorr.getDataApertura());
                statement.setDouble(5, contoCorr.getSaldo());
                statement.setInt(6, contoCorr.getTassoInteresse());
                statement.setInt(7, contoCorr.getTariffaAnnuale());
                statement.setString(8, contoCorr.getIndFatturazione().getNomeVia());
                statement.setString(9, contoCorr.getIndFatturazione().getNumCivico());
                statement.setInt(11, contoCorr.getIndFatturazione().getComune().getIdComune());
                statement.setInt(12, contoCorr.getIndFatturazione().getTipologiaVia().getIdVia());
            }
            statement.executeUpdate();

            if (fristTime) {
                Stato attivo= statoDAO.doRetriveByAttribute(ValoriStato.ATTIVO);
                contoCorr.setStatoConto(attivo);

                RelStatoConto relazione= new RelStatoConto(contoCorr.getDataApertura().toString(), attivo, contoCorr);
                if(!relStatoContoDAO.saveOrUpdate(relazione))
                    return false;

                contoCorr.setIban(iban);
                contoCorr.setPin(pin);
                contoCorr.setStatoConto(attivo);
                contoCorr.setSaldo(500.0);
                contoCorr.setTariffaAnnuale(20);
                contoCorr.setTassoInteresse(3);
            }

            result= true;

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(ContoCorrente contoCorr) {
        return false;
    }

    private String generateAlpha(int pos) {
        String[] alfa= {"A","B","C","D","E","F","G","H","I","L","M","N","O","P","Q","R","S","T","U","V","Z"};
        return alfa[pos];
    }

    private String generatealphaNum(Random rng) {
        String st= "";
        int numOrAlpha= rng.nextInt(10);
        while(st.length()<12) {
            if (numOrAlpha>=0 && numOrAlpha<4)
                st+=String.valueOf(rng.nextInt(10));
            else
                st+=generateAlpha(rng.nextInt(21));
        }
        return st;
    }
}
