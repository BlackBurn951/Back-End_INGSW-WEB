package com.example.progettowebtest.DAO.ContoCorrente_StatoConto;

import com.example.progettowebtest.ClassiRequest.IdentificativiUtente;
import com.example.progettowebtest.Connection.DbConn;
import com.example.progettowebtest.DAO.Indirizzo.IndirizzoDAO;
import com.example.progettowebtest.DAO.Indirizzo.IndirizzoDAOImpl;
import com.example.progettowebtest.DAO.MagnusDAO;
import com.example.progettowebtest.DAO.StatoDAO;
import com.example.progettowebtest.DAO.StatoDAOImpl;
import com.example.progettowebtest.DAO.Utente_Documenti.UtenteDAO;
import com.example.progettowebtest.DAO.Utente_Documenti.UtenteDAOImpl;
import com.example.progettowebtest.Model.ContoCorrente.ContoCorrente;
import com.example.progettowebtest.Model.ContoCorrente.Notifiche;
import com.example.progettowebtest.Model.ContoCorrente.RelStatoConto;
import com.example.progettowebtest.Model.Indirizzo.Indirizzo;
import com.example.progettowebtest.Model.Proxy.Transazione;
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
    public String pinChiaro;

    public ContoCorrenteDAOImpl() {};

    public String getPinChiaro() {return pinChiaro;}
    public void setPinChiaro(String pinChiaro) {this.pinChiaro = pinChiaro;}


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

            if(queryResult.next()) {
                intestatario= MagnusDAO.getInstance().getUtenteDAO().doRetriveByKey(queryResult.getString("cf"), IdentificativiUtente.CF);
                indFatturazione= MagnusDAO.getInstance().getIndirizzoDAO().doRetriveByKey(queryResult.getString("nome_via_fatturazione"), queryResult.getString("num_civico_fatturazione"),
                        queryResult.getInt("id_comune_fatturazione"), queryResult.getInt("id_via_fatturazione"));
                state= MagnusDAO.getInstance().getRelStatoContoDAO().doRetriveActualState(queryResult.getString("num_cc"));
                result= new ContoCorrente(queryResult.getString("num_cc"), queryResult.getString("iban"), queryResult.getString("pin_sicurezza"), queryResult.getDate("data_apertura").toString(),
                        queryResult.getDouble("saldo"), queryResult.getInt("tasso_interesse"), queryResult.getInt("tariffa_annuale"), indFatturazione, intestatario);
                result.setStatoConto(state);

                Vector<Transazione> bollettino= MagnusDAO.getInstance().getBollettinoDAO().doRetriveAllForCC(queryResult.getString("num_cc"));
                Vector<Transazione> bonificoSepa= MagnusDAO.getInstance().getBonificoSepaDAO().doRetriveAllForCC(queryResult.getString("num_cc"));
                Vector<Transazione> bonificoInt= MagnusDAO.getInstance().getBonificoInterDAO().doRetriveAllForCC(queryResult.getString("num_cc"));
                Vector<Transazione> deposito= MagnusDAO.getInstance().getDepositoDAO().doRetriveAllForCC(queryResult.getString("num_cc"));
                Vector<Transazione> prelievo= MagnusDAO.getInstance().getPrelievoDAO().doRetriveAllForCC(queryResult.getString("num_cc"));


                if(!bollettino.isEmpty()) {
                    for(Transazione trans: bollettino)
                        result.addTransazione(trans);
                }
                if(!bonificoSepa.isEmpty()) {
                    for(Transazione trans: bonificoSepa)
                        result.addTransazione(trans);
                }
                if(!bonificoInt.isEmpty()) {
                    for(Transazione trans: bonificoInt)
                        result.addTransazione(trans);
                }
                if(!deposito.isEmpty()) {
                    for(Transazione trans: deposito)
                        result.addTransazione(trans);
                }
                if(!prelievo.isEmpty()) {
                    for(Transazione trans: prelievo)
                        result.addTransazione(trans);
                }


                Vector<Notifiche> not= MagnusDAO.getInstance().getNotificheDAO().doRetriveAllForCC(result.getNumCC());
                for (Notifiche no : not) {
                    result.addNotifica(no);
                }


            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ContoCorrente doRetriveByAttribute(String attributo) {
        ContoCorrente result= null;
        Utente intestatario= null;
        Indirizzo indFatturazione= null;
        Stato state= null;

        String query= "select * from conto_corrente where cf= ?";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            statement.setString(1, attributo);

            ResultSet queryResult= statement.executeQuery();

            if(queryResult.next()) {
                intestatario= MagnusDAO.getInstance().getUtenteDAO().doRetriveByKey(queryResult.getString("cf"), IdentificativiUtente.CF);
                indFatturazione= MagnusDAO.getInstance().getIndirizzoDAO().doRetriveByKey(queryResult.getString("nome_via_fatturazione"), queryResult.getString("num_civico_fatturazione"),
                        queryResult.getInt("id_comune_fatturazione"), queryResult.getInt("id_via_fatturazione"));
                state= MagnusDAO.getInstance().getRelStatoContoDAO().doRetriveActualState(queryResult.getString("num_cc"));
                result= new ContoCorrente(queryResult.getString("num_cc"), queryResult.getString("iban"), queryResult.getString("pin_sicurezza"), queryResult.getDate("data_apertura").toString(),
                        queryResult.getDouble("saldo"), queryResult.getInt("tasso_interesse"), queryResult.getInt("tariffa_annuale"), indFatturazione, intestatario);
                result.setStatoConto(state);

                Vector<Transazione> bollettino= MagnusDAO.getInstance().getBollettinoDAO().doRetriveAllForCC(queryResult.getString("num_cc"));
                Vector<Transazione> bonificoSepa= MagnusDAO.getInstance().getBonificoSepaDAO().doRetriveAllForCC(queryResult.getString("num_cc"));
                Vector<Transazione> bonificoInt= MagnusDAO.getInstance().getBonificoInterDAO().doRetriveAllForCC(queryResult.getString("num_cc"));
                Vector<Transazione> deposito= MagnusDAO.getInstance().getDepositoDAO().doRetriveAllForCC(queryResult.getString("num_cc"));
                Vector<Transazione> prelievo= MagnusDAO.getInstance().getPrelievoDAO().doRetriveAllForCC(queryResult.getString("num_cc"));


                if(!bollettino.isEmpty()) {
                    for(Transazione trans: bollettino)
                        result.addTransazione(trans);
                }
                if(!bonificoSepa.isEmpty()) {
                    for(Transazione trans: bonificoSepa)
                        result.addTransazione(trans);
                }
                if(!bonificoInt.isEmpty()) {
                    for(Transazione trans: bonificoInt)
                        result.addTransazione(trans);
                }
                if(!deposito.isEmpty()) {
                    for(Transazione trans: deposito)
                        result.addTransazione(trans);
                }
                if(!prelievo.isEmpty()) {
                    for(Transazione trans: prelievo)
                        result.addTransazione(trans);
                }


                Vector<Notifiche> not= MagnusDAO.getInstance().getNotificheDAO().doRetriveAllForCC(result.getNumCC());
                for (Notifiche no : not) {
                    result.addNotifica(no);
                }

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
            setPinChiaro(pin);
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
                statement.setString(10, contoCorr.getIntestatario().getCodiceFiscale());
                statement.setInt(11, contoCorr.getIndFatturazione().getComune().getIdComune());
                statement.setInt(12, contoCorr.getIndFatturazione().getTipologiaVia().getIdVia());
            }
            int i= statement.executeUpdate();
            if(i>0) {

                if (fristTime) {
                    Stato attivo = MagnusDAO.getInstance().getStatoDAO().doRetriveByAttribute(ValoriStato.ATTIVO);
                    contoCorr.setStatoConto(attivo);

                    contoCorr.setNumCC(numConto);
                    contoCorr.setIban(iban);
                    contoCorr.setPin(pin);
                    contoCorr.setStatoConto(attivo);
                    contoCorr.setSaldo(500.0);
                    contoCorr.setTariffaAnnuale(20);
                    contoCorr.setTassoInteresse(3);

                    RelStatoConto relazione = new RelStatoConto(contoCorr.getDataApertura().toString(), attivo, contoCorr);
                    if (!MagnusDAO.getInstance().getRelStatoContoDAO().saveOrUpdate(relazione))
                        result = false;
                }

                result = true;
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean delete(String numCC) {
        String query= "delete from conto_corrente where num_cc= ?";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);

            statement.setString(1, numCC);

            return statement.executeUpdate()>0;

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    //Metodi di servizio
    private String generateAlpha(int pos) {
        String[] alfa= {"A","B","C","D","E","F","G","H","I","L","M","N","O","P","Q","R","S","T","U","V","Z"};
        return alfa[pos];
    }

    private String generatealphaNum(Random rng) {
        String st= "";
        int numOrAlpha;
        while(st.length()<12) {
            numOrAlpha= rng.nextInt(10);
            if (numOrAlpha>=0 && numOrAlpha<4)
                st+=String.valueOf(rng.nextInt(10));
            else
                st+=generateAlpha(rng.nextInt(21));
        }
        return st;
    }
}
