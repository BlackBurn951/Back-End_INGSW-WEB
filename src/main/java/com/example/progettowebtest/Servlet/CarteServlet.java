package com.example.progettowebtest.Servlet;

import com.example.progettowebtest.ClassiEmail.InvioCarta;
import com.example.progettowebtest.DAO.MagnusDAO;
import com.example.progettowebtest.EmailSender.SenderEmail;
import com.example.progettowebtest.Model.Carte.CartaCredito;
import com.example.progettowebtest.Model.Carte.CartaDebito;
import com.example.progettowebtest.Model.Carte.Carte;
import com.example.progettowebtest.Model.Carte.TipiCarte;
import com.example.progettowebtest.Model.ContoCorrente.ContoCorrente;
import com.example.progettowebtest.Model.Utente_Documenti.Utente;
import com.example.progettowebtest.Model.ValoriStato;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.cglib.core.Local;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import static com.example.progettowebtest.EmailSender.OTPGenerator.generateOTP;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CarteServlet {

    @GetMapping("/creaCarta")
    public List<String> creaCarta(HttpServletRequest request, @RequestParam("IDSession") String idSession, @RequestParam("tipoCarta") boolean tipoCarta) {
        HttpSession session = (HttpSession) request.getServletContext().getAttribute(idSession);

        Utente ut = (Utente) session.getAttribute("Utente");

        LocalDate dataAttuale= LocalDate.now();
        LocalDate dataArrivo = dataAttuale.plusYears(5);

        String numCartaProposto = generaNumeroCarta();
        String cvvProposto = generaCVV();
        String dataCreazione= dataAttuale.toString();
        String dataScadenza= dataArrivo.toString();
        String pinProposto = generateOTP();

        Vector<String> dati= new Vector<>();

        dati.add(numCartaProposto);
        dati.add(dataScadenza);
        dati.add(ut.getNome()+" "+ut.getCognome());
        dati.add(cvvProposto);
        dati.add(dataCreazione);
        dati.add(pinProposto);

        session.setAttribute("Dati carta", dati);
        session.setAttribute("Tipo carta", tipoCarta);

        return dati;

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
