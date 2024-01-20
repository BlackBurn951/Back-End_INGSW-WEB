package com.example.progettowebtest.Servlet;

import com.example.progettowebtest.ClassiRequest.DatiCarta;
import com.example.progettowebtest.DAO.MagnusDAO;
import com.example.progettowebtest.EmailSender.EmailData;
import com.example.progettowebtest.EmailSender.EmailTemplateLoader;
import com.example.progettowebtest.EmailSender.SendEmailController;
import com.example.progettowebtest.Model.Carte.CartaProxy;
import com.example.progettowebtest.Model.Carte.Carte;
import com.example.progettowebtest.Model.Carte.TipiCarte;
import com.example.progettowebtest.Model.ContoCorrente.ContoCorrente;
import com.example.progettowebtest.Model.Utente_Documenti.Utente;
import com.example.progettowebtest.Model.ValoriStato;
import com.google.api.services.gmail.model.Message;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.example.progettowebtest.EmailSender.EmailService.*;
import static com.example.progettowebtest.EmailSender.OTPGenerator.generateOTP;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CarteServlet {

    @PostMapping("/creaCarta")
    public boolean creaCarta(HttpServletRequest request, HttpServletResponse response, @RequestParam("IDSession") String idSession, @RequestBody DatiCarta dati) {
        HttpSession session = (HttpSession) request.getServletContext().getAttribute(idSession);
        Utente ut = (Utente) session.getAttribute("Utente");
        ContoCorrente cc = (ContoCorrente) session.getAttribute("Conto");
        String generatedOTP = generateOTP();


        double canone= 0.0, fido= 0.0;
        TipiCarte tipo;
        if(dati.getCredito()) {
            canone = 10.0;
            fido= 2000.0;
            tipo= TipiCarte.CREDITO;
        }
        else {
            canone = 0.60;
            tipo= TipiCarte.DEBITO;
        }
        Carte carta = new CartaProxy(dati.getNumeroCarta(), true, LocalDate.now().toString(), dati.getDataScadenza(), dati.getCvv(), false, canone, generatedOTP, fido, MagnusDAO.getInstance().getStatoDAO().doRetriveByAttribute(ValoriStato.ATTIVO), tipo);
        return true;
    }
}
