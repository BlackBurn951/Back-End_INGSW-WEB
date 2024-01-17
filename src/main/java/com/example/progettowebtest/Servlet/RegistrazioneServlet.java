package com.example.progettowebtest.Servlet;

import com.example.progettowebtest.ClassiRequest.DatiControlloUtente;
import com.example.progettowebtest.ClassiRequest.DatiRegistrazione;
import com.example.progettowebtest.DAO.MagnusDAO;
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

    //RICORDARSI DI CAPIRE COME FARE IL LOGIN IN AUTOMATICO
    @PostMapping("/emailCheck")
    public int emailCheck(HttpServletRequest request, HttpServletResponse response, @RequestBody DatiControlloUtente dati) {
        int result= 2;

        if(MagnusDAO.getInstance().getUtenteDAO().doRetriveByKey(dati.getEmail(), IdentificativiUtente.EMAIL)!=null)
            result= 0;
        else if(MagnusDAO.getInstance().getUtenteDAO().doRetriveByKey(dati.getCf(), IdentificativiUtente.CF)!=null)
            result= 1;
        else {
            HttpSession session= request.getSession(false);
            if(session!=null)
                session.invalidate();
            session= request.getSession(true);
            response.setHeader("Session-ID", session.getId());
            session.setMaxInactiveInterval(1800);
            request.getServletContext().setAttribute(session.getId(), session);
        }
        return result;
    }

    @GetMapping("/checkOTP")
    public String checkOTP(HttpServletRequest request, @RequestParam("otpSend") String otpSend, @RequestParam("IDSession") String idSess) {
        String response;

        HttpSession session= (HttpSession) request.getServletContext().getAttribute(idSess);
        //System.out.println("id sessione presa dal context: "+ session.getId());
        long minutiAttuali= TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis());

        if(session!=null && ((minutiAttuali - (long) session.getAttribute("TempoInvioOTP")) <= 10)) {
            if(session.getAttribute("control").equals(otpSend)) {
                response = "OTP corretto";
                System.out.println(response);
            }
            else {
                response = "OTP errato";
                System.out.println(response);
            }
        }
        else if(session!=null && ((minutiAttuali - (long) session.getAttribute("TempoInvioOTP")) > 10))
            response= "Sessione scaduta";
        else {
            System.out.println("Server: sessione non trovata!!!");
            response= "Sessione non esistente!!!";
        }

        return response;
    }

    @PostMapping("/insertUser")
    public boolean insertUser(HttpServletRequest request, @RequestParam("IDSession") String idSession, @RequestBody DatiRegistrazione dati) {
        boolean result= false;

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

            //Invio contratto
            String indirizzoRes= dati.getTipoStradaRes()+" "+dati.getNomeStradaRes()+" "+dati.getNumCivicoRes()+" "+dati.getCittaRes()+" "+dati.getCapRes()+" "+dati.getProvinciaRes()+" "+dati.getRegioneRes();

            String emailTemplate = EmailTemplateLoader.loadEmailTemplate("/email_pdf_template.html");
            String pdf_html = emailTemplate
                    .replace("EMAIL_DESTINATARIO", dati.getEmail())
                    .replace("NOME_COGNOME", dati.getNome()+" "+dati.getCognome())
                    .replace("DATA_NASCITA", dati.getDataNascita())
                    .replace("INDIRIZZO", indirizzoRes)
                    .replace("NUMERO_TELEFONO", dati.getCellulare())
                    .replace("DATA_FIRMA", data.toString())
                    .replace("PINDASERVER", MagnusDAO.getInstance().getContoCorrenteDAO().getPinChiaro());
            MagnusDAO.getInstance().getContoCorrenteDAO().setPinChiaro("");

            PDDocument pdfFile = CreaPDFConfermaConto.creaPDFconto(dati.getNome()+" "+dati.getCognome(), dati.getDataNascita(), indirizzoRes, dati.getCellulare(), dati.getEmail(), data.toString());
            Message message = EmailService.createMessageWithAttachment(pdf_html, "caesar.magnus.info@gmail.com", dati.getEmail(), "Conferma registrazione e dati conto", pdfFile);
            sendMessage(getService(), "caesar.magnus.info@gmail.com", message);


            //Aggiornamento sessione
            HttpSession session= (HttpSession) request.getServletContext().getAttribute(idSession);
            if(session==null)
                session= request.getSession(true);
            session.setMaxInactiveInterval(21600);

        }catch (NullPointerException | GeneralSecurityException | IOException | MessagingException e ) {
            e.printStackTrace();
        }
        return result;
    }
}
