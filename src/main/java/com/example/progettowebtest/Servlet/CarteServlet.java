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

    @GetMapping("/confermaCarta")
    public boolean confermaCarta(HttpServletRequest request, @RequestParam("IDSession") String idSession) {
        boolean result= false;

        HttpSession session = (HttpSession) request.getServletContext().getAttribute(idSession);

        boolean tipo= (boolean)session.getAttribute("Tipo carta");
        Utente ut= (Utente)session.getAttribute("Utente");
        ContoCorrente cc= (ContoCorrente)session.getAttribute("Conto");
        Vector<String> dati= (Vector<String>)session.getAttribute("Dati carta");

        Carte carta= null;

        if(tipo) {
            carta = new CartaCredito(dati.get(0), true, dati.get(4), dati.get(1), dati.get(3), false, 10.0, dati.get(5),
                    MagnusDAO.getInstance().getStatoDAO().doRetriveByAttribute(ValoriStato.ATTIVO), cc, 2000.0);

            if(MagnusDAO.getInstance().getCarteDAO().saveOrUpdate(carta, TipiCarte.CREDITO)) {
                InvioCarta datiInvio = new InvioCarta(dati.get(2), dati.get(5), dati.get(0), dati.get(1), dati.get(3), "Carta di credito");
                SenderEmail.sendEmails(null, null, datiInvio, ut.getEmail());

                session.removeAttribute("Dati carta");
                session.removeAttribute("Tipo carta");
                result= true;
            }
        }
        else {
            carta = new CartaDebito(dati.get(0), true, dati.get(4), dati.get(1), dati.get(3), false, 0.60, dati.get(5),
                    MagnusDAO.getInstance().getStatoDAO().doRetriveByAttribute(ValoriStato.ATTIVO), cc);
            if(MagnusDAO.getInstance().getCarteDAO().saveOrUpdate(carta, TipiCarte.DEBITO)) {
                InvioCarta datiInvio= new InvioCarta(dati.get(2), dati.get(5), dati.get(0), dati.get(1), dati.get(3), "Carta di debito");
                SenderEmail.sendEmails(null,null, datiInvio, ut.getEmail());

                session.removeAttribute("Dati carta");
                session.removeAttribute("Tipo carta");
                result= true;
            }
        }
        return result;
    }




    private String generaNumeroCarta() {
        Random random = new Random();
        StringBuilder stringaCasuale = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            int numeroCasuale = random.nextInt(10);
            stringaCasuale.append(numeroCasuale);
            if ((i + 1) % 4 == 0 && i != 15) {
                stringaCasuale.append("-");
            }
        }
        return stringaCasuale.toString();
    }

    private String generaCVV() {
        Random random = new Random();
        StringBuilder stringaCasuale = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            int numeroCasuale = random.nextInt(10);
            stringaCasuale.append(numeroCasuale);
        }
        return stringaCasuale.toString();
    }


}
