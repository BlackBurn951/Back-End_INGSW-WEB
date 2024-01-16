package com.example.progettowebtest.Servlet;

import com.example.progettowebtest.ClassiRequest.DatiControlloUtente;
import com.example.progettowebtest.ClassiRequest.DatiRegistrazione;
import com.example.progettowebtest.DAO.ContoCorrente_StatoConto.ContoCorrenteDAO;
import com.example.progettowebtest.DAO.ContoCorrente_StatoConto.ContoCorrenteDAOImpl;
import com.example.progettowebtest.DAO.Indirizzo.*;
import com.example.progettowebtest.DAO.StatoDAO;
import com.example.progettowebtest.DAO.StatoDAOImpl;
import com.example.progettowebtest.DAO.Utente_Documenti.*;
import com.example.progettowebtest.ClassiRequest.IdentificativiUtente;
import com.example.progettowebtest.Model.ContoCorrente.ContoCorrente;
import com.example.progettowebtest.Model.Indirizzo.ColonneDatiComune;
import com.example.progettowebtest.Model.Indirizzo.DatiComune;
import com.example.progettowebtest.Model.Indirizzo.Indirizzo;
import com.example.progettowebtest.Model.Indirizzo.TipoVia;
import com.example.progettowebtest.Model.Stato;
import com.example.progettowebtest.Model.Utente_Documenti.*;
import com.example.progettowebtest.Model.ValoriStato;
import jakarta.servlet.http.*;
import org.mortbay.util.ajax.JSON;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Vector;


@RestController
@CrossOrigin(origins = "http://localhost:4200", exposedHeaders = "Session-ID")
public class RegistrazioneServlet extends HttpServlet {
    private UtenteDAO utenteDAO= UtenteDAOImpl.getInstance();
    private IndirizzoDAO indirizzoDAO= IndirizzoDAOImpl.getInstance();
    private TipoViaDAO tipoViaDAO= TipoViaDAOImpl.getInstance();
    private DatiComuneDAO datiComuneDAO= DatiComuneDAOImpl.getInstance();
    private PatenteDAO patenteDAO= PatenteDAOImpl.getInstance();
    private CartaIdentitaDAO cartaIdentitaDAO= CartaIdentitaDAOImpl.getInstance();
    private PassaportoDAO passaportoDAO= PassaportoDAOImpl.getInstance();
    private StatoDAO statoDAO= StatoDAOImpl.getInstance();
    private ContoCorrenteDAO contoCorrenteDAO= ContoCorrenteDAOImpl.getInstance();

    //DA SPOSTARE
    @PostMapping("/emailCheck")
    public int emailCheck(@RequestBody DatiControlloUtente dati) {
        int result= 2;

        if(utenteDAO.doRetriveByKey(dati.getEmail(), IdentificativiUtente.EMAIL)!=null)
            result= 0;
        else if(utenteDAO.doRetriveByKey(dati.getCf(), IdentificativiUtente.CF)!=null)
            result= 1;

        return result;
    }


    //DA CONTROLLARE IL CONTO DEL MINUTAGGIO
    @GetMapping("/checkOTP")
    public String checkOTP(HttpServletRequest request, @RequestParam("otpSend") String otpSend, @RequestParam("IdSession") String idSess) {
        String response;

        HttpSession session= (HttpSession) request.getServletContext().getAttribute(idSess);
        long creationTime = session.getCreationTime(); // Tempo di creazione della sessione in millisecondi
        long currentTime = System.currentTimeMillis(); // Tempo corrente in millisecondi
        long tenMinutesInMillis = 10 * 60 * 1000; // 10 minuti in millisecondi
        if(session!=null && (currentTime - creationTime < tenMinutesInMillis)) {
            if(session.getAttribute("control").equals(otpSend)) {
                response = "OTP corretto";
                System.out.println(response);
            }
            else {
                response = "OTP errato";
                System.out.println(response);
            }
        }
        else if(session!=null && (currentTime - creationTime >= tenMinutesInMillis))
            response= "Sessione scaduta";
        else {
            System.out.println("Server: sessione non trovata!!!");
            response= "Sessione non esistente!!!";
        }

        return response;
    }

    @PostMapping("/insertUser")
    public boolean insertUser(@RequestBody DatiRegistrazione dati) {
        boolean result= false;

        try{
            Utente ut= null;
            switch (dati.getTipoDoc()) {
                case "patente":
                    Patente pat= new Patente(dati.getNomeDocumento(), dati.getCognomeDocumento(), dati.getNazionalita(),
                        dati.getComuneNascitaDoc(), dati.getSessoDoc(), dati.getProvNascitaDoc(), dati.getDataNascitaDocumento(), dati.getIdDoc(), dati.getDataEmissione(), dati.getDataScadenza(), dati.getComuneAutorita());
                    if(!patenteDAO.saveOrUpdate(pat))
                        return false;

                    ut= new Utente(dati.getNome(), dati.getCognome(), dati.getCittadinanza(), dati.getComuneNas(), dati.getSesso(), dati.getProvNas(),
                            dati.getCellulare(), dati.getDataNascita(), dati.getCf(), dati.getEmail(), dati.getPassword(), dati.getOccupazione(), (double)dati.getReddito(), pat);
                    break;
                case "cartaIdentita":
                    CartaIdentita identita= new CartaIdentita(dati.getNomeDocumento(), dati.getCognomeDocumento(), dati.getNazionalita(),
                            dati.getComuneNascitaDoc(), dati.getSessoDoc(), dati.getProvNascitaDoc(), dati.getDataNascitaDocumento(), dati.getIdDoc(), dati.getDataEmissione(), dati.getDataScadenza(), dati.getComuneAutorita());
                    if(!cartaIdentitaDAO.saveOrUpdate(identita))
                        return false;

                    ut= new Utente(dati.getNome(), dati.getCognome(), dati.getCittadinanza(), dati.getComuneNas(), dati.getSesso(), dati.getProvNas(),
                            dati.getCellulare(), dati.getDataNascita(), dati.getCf(), dati.getEmail(), dati.getPassword(), dati.getOccupazione(), (double)dati.getReddito(), identita);
                    break;
                case "passaporto":
                    Passaporto passa= new Passaporto(dati.getNomeDocumento(), dati.getCognomeDocumento(), dati.getNazionalita(),
                            dati.getComuneNascitaDoc(), dati.getSessoDoc(), dati.getProvNascitaDoc(), dati.getDataNascitaDocumento(), dati.getIdDoc(), dati.getDataEmissione(), dati.getDataScadenza(), dati.getComuneAutorita());
                    if(!passaportoDAO.saveOrUpdate(passa))
                        return false;

                    ut= new Utente(dati.getNome(), dati.getCognome(), dati.getCittadinanza(), dati.getComuneNas(), dati.getSesso(), dati.getProvNas(),
                            dati.getCellulare(), dati.getDataNascita(), dati.getCf(), dati.getEmail(), dati.getPassword(), dati.getOccupazione(), (double)dati.getReddito(), passa);
                    break;
            }

            TipoVia tipo= tipoViaDAO.doRetriveByAttribute(dati.getTipoStradaRes());
            Vector<DatiComune> queryDatiComune= datiComuneDAO.doRetriveByAttribute(dati.getCittaRes(), ColonneDatiComune.NOME_COMUNE);
            DatiComune comune= queryDatiComune.get(0);

            Indirizzo res= new Indirizzo(tipo, dati.getNomeStradaRes(), dati.getNumCivicoRes(), comune);
            if(!indirizzoDAO.saveOrUpdate(res))
                return false;

            ut.addAddress(res);
            if(dati.getCittaDom()!=null) {
                tipo= tipoViaDAO.doRetriveByAttribute(dati.getTipoStradaDom());
                queryDatiComune= datiComuneDAO.doRetriveByAttribute(dati.getCittaDom(), ColonneDatiComune.NOME_COMUNE);
                comune= queryDatiComune.get(0);

                Indirizzo dom= new Indirizzo(tipo, dati.getNomeStradaDom(), dati.getNumCivicoDom(), comune);

                ut.addAddress(dom);
                if(!indirizzoDAO.saveOrUpdate(dom))
                    return false;
            }

            if(!utenteDAO.saveOrUpdate(ut))
                return false;

            tipo= tipoViaDAO.doRetriveByAttribute(dati.getTipoStradaFat());
            queryDatiComune= datiComuneDAO.doRetriveByAttribute(dati.getCittaFat(), ColonneDatiComune.NOME_COMUNE);
            comune= queryDatiComune.get(0);
            Indirizzo indFat= new Indirizzo(tipo, dati.getNomeStradaFat(), dati.getNumCivicoFat(), comune);

            ContoCorrente cc= new ContoCorrente();
            cc.setIndFatturazione(indFat);
            cc.setIntestatario(ut);
            LocalDate data= LocalDate.now();
            cc.setDataApertura(data.toString());

            contoCorrenteDAO.saveOrUpdate(cc, true);

        }catch (NullPointerException e) {
            e.printStackTrace();
        }
        return result;
    }
}
