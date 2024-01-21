package com.example.progettowebtest.Servlet;

import com.example.progettowebtest.ClassiEmail.InvioCarta;
import com.example.progettowebtest.ClassiRequest.CambioStatoCarta;
import com.example.progettowebtest.ClassiRequest.Card;
import com.example.progettowebtest.DAO.MagnusDAO;
import com.example.progettowebtest.EmailSender.SenderEmail;
import com.example.progettowebtest.Model.Carte.*;
import com.example.progettowebtest.Model.ContoCorrente.ContoCorrente;
import com.example.progettowebtest.Model.Stato;
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

    @PostMapping("/cambiaStatoCarta")
    public boolean cambiaStato(HttpServletRequest request, @RequestParam("IDSession") String idSession, @RequestBody CambioStatoCarta dati){
        boolean result;  //True -> eliminato  False -> attivata/disattivata

        HttpSession session = (HttpSession) request.getServletContext().getAttribute(idSession);

        Carte carta;
        Stato statoCarta;

        if(dati.isTipoCarta()) {
            carta = MagnusDAO.getInstance().getCarteDAO().doRetriveByKey(dati.getNumCarta(), TipiCarte.CREDITO, true);
            if (dati.getStato() == 1) {
                statoCarta = MagnusDAO.getInstance().getRelStatoCarteDAO().doRetriveActualState(dati.getNumCarta(), TipiCarte.CREDITO);

                if (statoCarta.getValoreStato().equals("attivo")) {
                    RelStatoCarta rel= MagnusDAO.getInstance().getRelStatoCarteDAO().doRetriveActualRel(dati.getNumCarta(), TipiCarte.CREDITO, statoCarta);
                    rel.setDataFineStato(LocalDate.now().toString());

                    MagnusDAO.getInstance().getRelStatoCarteDAO().saveOrUpdate(rel, TipiCarte.CREDITO);

                    statoCarta= MagnusDAO.getInstance().getStatoDAO().doRetriveByAttribute(ValoriStato.SOSPESO);
                    rel= new RelStatoCarta(LocalDate.now().toString(), statoCarta, carta);

                    MagnusDAO.getInstance().getRelStatoCarteDAO().saveOrUpdate(rel, TipiCarte.CREDITO);

                    result = false;
                }else{
                    RelStatoCarta rel= MagnusDAO.getInstance().getRelStatoCarteDAO().doRetriveActualRel(dati.getNumCarta(), TipiCarte.CREDITO, statoCarta);
                    rel.setDataFineStato(LocalDate.now().toString());

                    MagnusDAO.getInstance().getRelStatoCarteDAO().saveOrUpdate(rel, TipiCarte.CREDITO);

                    statoCarta= MagnusDAO.getInstance().getStatoDAO().doRetriveByAttribute(ValoriStato.ATTIVO);
                    rel= new RelStatoCarta(LocalDate.now().toString(), statoCarta, carta);

                    MagnusDAO.getInstance().getRelStatoCarteDAO().saveOrUpdate(rel, TipiCarte.CREDITO);

                    result = false;
                }

            }else {
                MagnusDAO.getInstance().getRelStatoCarteDAO().delete(carta.getNumCarta(), TipiCarte.CREDITO);
                MagnusDAO.getInstance().getCarteDAO().delete(carta, TipiCarte.CREDITO);

                result= true;
            }

        }
        else {
            carta = MagnusDAO.getInstance().getCarteDAO().doRetriveByKey(dati.getNumCarta(), TipiCarte.DEBITO, true);

            if (dati.getStato() == 1) {
                statoCarta = MagnusDAO.getInstance().getRelStatoCarteDAO().doRetriveActualState(dati.getNumCarta(), TipiCarte.DEBITO);

                if (statoCarta.getValoreStato().equals("attivo")) {
                    RelStatoCarta rel= MagnusDAO.getInstance().getRelStatoCarteDAO().doRetriveActualRel(dati.getNumCarta(), TipiCarte.DEBITO, statoCarta);
                    rel.setDataFineStato(LocalDate.now().toString());

                    MagnusDAO.getInstance().getRelStatoCarteDAO().saveOrUpdate(rel, TipiCarte.DEBITO);

                    statoCarta= MagnusDAO.getInstance().getStatoDAO().doRetriveByAttribute(ValoriStato.SOSPESO);
                    rel= new RelStatoCarta(LocalDate.now().toString(), statoCarta, carta);

                    MagnusDAO.getInstance().getRelStatoCarteDAO().saveOrUpdate(rel, TipiCarte.DEBITO);

                    result= false;
                }else{
                    RelStatoCarta rel= MagnusDAO.getInstance().getRelStatoCarteDAO().doRetriveActualRel(dati.getNumCarta(), TipiCarte.DEBITO, statoCarta);
                    rel.setDataFineStato(LocalDate.now().toString());

                    MagnusDAO.getInstance().getRelStatoCarteDAO().saveOrUpdate(rel, TipiCarte.DEBITO);

                    statoCarta= MagnusDAO.getInstance().getStatoDAO().doRetriveByAttribute(ValoriStato.ATTIVO);
                    rel= new RelStatoCarta(LocalDate.now().toString(), statoCarta, carta);

                    MagnusDAO.getInstance().getRelStatoCarteDAO().saveOrUpdate(rel, TipiCarte.DEBITO);

                    result= false;
                }
            } else {
                MagnusDAO.getInstance().getRelStatoCarteDAO().delete(carta.getNumCarta(), TipiCarte.DEBITO);
                MagnusDAO.getInstance().getCarteDAO().delete(carta, TipiCarte.DEBITO);

                result = true;
            }
        }
        return result;
    }

    @GetMapping("/prendiCarte")
    public List<Card> prendiCarte(HttpServletRequest request, @RequestParam("IDSession") String idSession) {
        Vector<Card> result= new Vector<>();

        HttpSession session = (HttpSession) request.getServletContext().getAttribute(idSession);
        Utente ut = (Utente) session.getAttribute("Utente");
        ContoCorrente cc= (ContoCorrente)session.getAttribute("Conto");

        Vector<Carte> carteConto= MagnusDAO.getInstance().getCarteDAO().doRetriveAllForCC(cc.getNumCC());

        for(Carte ct: carteConto) {
            result.add(new Card(ct.getNumCarta(), ct.isPagamentoOnline(), ut.getNome()+" "+ut.getCognome(), ct.getDataScadenza().toString(), ct.getCvv(), ct.getCanoneMensile(), ct.getFido(), ct.getStatoCarta().getValoreStato()));
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
