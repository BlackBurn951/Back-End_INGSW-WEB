package com.example.progettowebtest.Servlet;

import com.example.progettowebtest.Model.ContoCorrente.ContoCorrente;
import com.example.progettowebtest.Model.Utente_Documenti.Utente;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import static com.example.progettowebtest.EmailSender.OTPGenerator.generateOTP;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CarteServlet {

    @PostMapping("/creaCarta")
    public boolean creaCarta(HttpServletRequest request, HttpServletResponse response, @RequestParam("IDSession") String idSession) {
        HttpSession session = (HttpSession) request.getServletContext().getAttribute(idSession);
        Utente ut = (Utente) session.getAttribute("Utente");
        ContoCorrente cc = (ContoCorrente) session.getAttribute("Conto");
        String generatedOTP = generateOTP();

        /*
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
        */
        return true;

    }
}
