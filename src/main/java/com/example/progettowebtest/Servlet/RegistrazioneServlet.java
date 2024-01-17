package com.example.progettowebtest.Servlet;

import com.example.progettowebtest.ClassiRequest.DatiControlloUtente;
import com.example.progettowebtest.ClassiRequest.DatiRegistrazione;
import com.example.progettowebtest.DAO.ContoCorrente_StatoConto.ContoCorrenteDAO;
import com.example.progettowebtest.DAO.ContoCorrente_StatoConto.ContoCorrenteDAOImpl;
import com.example.progettowebtest.DAO.Indirizzo.*;
import com.example.progettowebtest.DAO.MagnusDAO;
import com.example.progettowebtest.DAO.StatoDAO;
import com.example.progettowebtest.DAO.StatoDAOImpl;
import com.example.progettowebtest.DAO.Utente_Documenti.*;
import com.example.progettowebtest.ClassiRequest.IdentificativiUtente;
import com.example.progettowebtest.EmailSender.CreaPDFConfermaConto;
import com.example.progettowebtest.EmailSender.EmailService;
import com.example.progettowebtest.EmailSender.EmailTemplateLoader;
import com.example.progettowebtest.Model.ContoCorrente.ContoCorrente;
import com.example.progettowebtest.Model.Indirizzo.ColonneDatiComune;
import com.example.progettowebtest.Model.Indirizzo.DatiComune;
import com.example.progettowebtest.Model.Indirizzo.Indirizzo;
import com.example.progettowebtest.Model.Indirizzo.TipoVia;
import com.example.progettowebtest.Model.Utente_Documenti.*;
import com.google.api.services.gmail.model.Message;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import static com.example.progettowebtest.EmailSender.EmailService.getService;
import static com.example.progettowebtest.EmailSender.EmailService.sendMessage;


@RestController
@CrossOrigin(origins = "http://localhost:4200", exposedHeaders = "Session-ID")
public class RegistrazioneServlet extends HttpServlet {


    //DA SPOSTARE
    @PostMapping("/emailCheck")
    public int emailCheck(@RequestBody DatiControlloUtente dati) {
        int result= 2;

        if(MagnusDAO.getInstance().getUtenteDAO().doRetriveByKey(dati.getEmail(), IdentificativiUtente.EMAIL)!=null)
            result= 0;
        else if(MagnusDAO.getInstance().getUtenteDAO().doRetriveByKey(dati.getCf(), IdentificativiUtente.CF)!=null)
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
        System.out.println(dati.getComuneNas());
        try{
            Utente ut= null;
            if(dati.getTipoDoc().equals("patente")) {
                Patente pat = new Patente(dati.getNomeDocumento(), dati.getCognomeDocumento(), dati.getNazionalita(),
                        dati.getComuneNascitaDoc(), dati.getSessoDoc(), dati.getProvNascitaDoc(), dati.getDataNascitaDocumento(), dati.getIdDoc(), dati.getDataEmissione(), dati.getDataScadenza(), dati.getComuneAutorita());
                if (!MagnusDAO.getInstance().getPatenteDAO().saveOrUpdate(pat))
                    return false;

                ut = new Utente(dati.getNome(), dati.getCognome(), dati.getCittadinanza(), dati.getComuneNas(), dati.getSesso(), dati.getProvNas(),
                        dati.getCellulare(), dati.getDataNascita(), dati.getCf(), dati.getEmail(), dati.getPassword(), dati.getOccupazione(), (double) dati.getReddito(), pat);
            }else if(dati.getTipoDoc().equals("cartaIdentita")) {
                CartaIdentita identita = new CartaIdentita(dati.getNomeDocumento(), dati.getCognomeDocumento(), dati.getNazionalita(),
                        dati.getComuneNascitaDoc(), dati.getSessoDoc(), dati.getProvNascitaDoc(), dati.getDataNascitaDocumento(), dati.getIdDoc(), dati.getDataEmissione(), dati.getDataScadenza(), dati.getComuneAutorita());
                if (!MagnusDAO.getInstance().getCartaIdentitaDAO().saveOrUpdate(identita))
                    return false;

                ut = new Utente(dati.getNome(), dati.getCognome(), dati.getCittadinanza(), dati.getComuneNas(), dati.getSesso(), dati.getProvNas(),
                        dati.getCellulare(), dati.getDataNascita(), dati.getCf(), dati.getEmail(), dati.getPassword(), dati.getOccupazione(), (double) dati.getReddito(), identita);
            }else if(dati.getTipoDoc().equals("passaporto")) {
                    Passaporto passa= new Passaporto(dati.getNomeDocumento(), dati.getCognomeDocumento(), dati.getNazionalita(),
                            dati.getComuneNascitaDoc(), dati.getSessoDoc(), dati.getProvNascitaDoc(), dati.getDataNascitaDocumento(), dati.getIdDoc(), dati.getDataEmissione(), dati.getDataScadenza(), dati.getComuneAutorita());
                    if(!MagnusDAO.getInstance().getPassaportoDAO().saveOrUpdate(passa))
                        return false;

                    ut= new Utente(dati.getNome(), dati.getCognome(), dati.getCittadinanza(), dati.getComuneNas(), dati.getSesso(), dati.getProvNas(),
                            dati.getCellulare(), dati.getDataNascita(), dati.getCf(), dati.getEmail(), dati.getPassword(), dati.getOccupazione(), (double)dati.getReddito(), passa);
            }

            TipoVia tipo= MagnusDAO.getInstance().getTipoViaDAO().doRetriveByAttribute(dati.getTipoStradaRes());
            Vector<DatiComune> queryDatiComune= MagnusDAO.getInstance().getDatiComuneDAO().doRetriveByAttribute(dati.getCittaRes(), ColonneDatiComune.NOME_COMUNE);
            DatiComune comune= queryDatiComune.get(0);

            Indirizzo res= new Indirizzo(tipo, dati.getNomeStradaRes(), dati.getNumCivicoRes(), comune);
            if(!MagnusDAO.getInstance().getIndirizzoDAO().saveOrUpdate(res))
                return false;

            assert ut != null;
            ut.addAddress(res);
            if(dati.getCittaDom()!=null) {
                tipo= MagnusDAO.getInstance().getTipoViaDAO().doRetriveByAttribute(dati.getTipoStradaDom());
                queryDatiComune= MagnusDAO.getInstance().getDatiComuneDAO().doRetriveByAttribute(dati.getCittaDom(), ColonneDatiComune.NOME_COMUNE);
                comune= queryDatiComune.get(0);

                Indirizzo dom= new Indirizzo(tipo, dati.getNomeStradaDom(), dati.getNumCivicoDom(), comune);

                ut.addAddress(dom);
                if(!MagnusDAO.getInstance().getIndirizzoDAO().saveOrUpdate(dom))
                    return false;
            }

            if(!MagnusDAO.getInstance().getUtenteDAO().saveOrUpdate(ut))
                return false;

            ContoCorrente cc= new ContoCorrente();

            if(dati.getCittaFat() != null){
                tipo= MagnusDAO.getInstance().getTipoViaDAO().doRetriveByAttribute(dati.getTipoStradaFat());
                queryDatiComune= MagnusDAO.getInstance().getDatiComuneDAO().doRetriveByAttribute(dati.getCittaFat(), ColonneDatiComune.NOME_COMUNE);
                comune = queryDatiComune.get(0);
                Indirizzo indFat= new Indirizzo(tipo, dati.getNomeStradaFat(), dati.getNumCivicoFat(), comune);

                cc.setIndFatturazione(indFat);
            }else{
                cc.setIndFatturazione(res);
            }

            cc.setIntestatario(ut);
            LocalDate data= LocalDate.now();
            cc.setDataApertura(data.toString());

            MagnusDAO.getInstance().getContoCorrenteDAO().saveOrUpdate(cc, true);

        }catch (NullPointerException e) {
            e.printStackTrace();
        }
        return result;
    }
}
