package com.example.progettowebtest.Servlet;

import com.example.progettowebtest.ClassiEmail.InvioCarta;
import com.example.progettowebtest.ClassiRequest.CambioStatoCarta;
import com.example.progettowebtest.ClassiRequest.Card;
import com.example.progettowebtest.DAO.MagnusDAO;
import com.example.progettowebtest.EmailSender.SenderEmail;
import com.example.progettowebtest.Model.Carte.*;
import com.example.progettowebtest.Model.ContoCorrente.ContoCorrente;
import com.example.progettowebtest.Model.ContoCorrente.Notifiche;
import com.example.progettowebtest.Model.ContoCorrente.PresetNotifiche;
import com.example.progettowebtest.Model.Stato;
import com.example.progettowebtest.Model.Utente_Documenti.Utente;
import com.example.progettowebtest.Model.ValoriStato;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import static com.example.progettowebtest.EmailSender.OTPGenerator.generateOTP;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CarteServlet {
    public CarteServlet() {
    }

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

        Carte carta;

        if(tipo) {
            carta = new CartaCredito(dati.get(0), true, dati.get(4), dati.get(1), dati.get(3), false, 10.0, dati.get(5),
                    MagnusDAO.getInstance().getStatoDAO().doRetriveByAttribute(ValoriStato.ATTIVO), cc, 2000.0);

            if(MagnusDAO.getInstance().getCarteDAO().saveOrUpdate(carta, TipiCarte.CREDITO)) {
                InvioCarta datiInvio = new InvioCarta(dati.get(2), dati.get(5), dati.get(0), dati.get(1), dati.get(3), "Carta di credito");
                SenderEmail.sendEmails(null, null, datiInvio, ut.getEmail());

                session.removeAttribute("Dati carta");
                session.removeAttribute("Tipo carta");

                Notifiche not= new Notifiche(PresetNotifiche.NOTIFICA_CREAZIONE_CARTA_PT_I+"credito"+PresetNotifiche.NOTIFICA_CREAZIONE_CARTA_PT_II+LocalDate.now(), false);
                MagnusDAO.getInstance().getNotificheDAO().saveOrUpdate(not, cc.getNumCC());
                cc.addNotifica(not);

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

                Notifiche not= new Notifiche(PresetNotifiche.NOTIFICA_CREAZIONE_CARTA_PT_I+"debito"+PresetNotifiche.NOTIFICA_CREAZIONE_CARTA_PT_II+LocalDate.now(), false);
                MagnusDAO.getInstance().getNotificheDAO().saveOrUpdate(not, cc.getNumCC());
                cc.addNotifica(not);

                result= true;
            }
        }
        return result;
    }

    @PostMapping("/cambiaStatoCarta")
    public boolean cambiaStatoCarta(HttpServletRequest request, @RequestParam("IDSession") String idSession, @RequestBody CambioStatoCarta dati){
        boolean result;  //True -> eliminato  False -> attivata/disattivata

        HttpSession session= (HttpSession)request.getServletContext().getAttribute(idSession);

        ContoCorrente cc= (ContoCorrente)session.getAttribute("Conto");
        Carte carta;
        Stato statoCarta;
        Notifiche not;

        if(dati.isTipoCarta()) {
            carta = MagnusDAO.getInstance().getCarteDAO().doRetriveByKey(dati.getNumCarta(), TipiCarte.CREDITO, true);
            if (dati.getStato() == 1) {
                statoCarta = MagnusDAO.getInstance().getRelStatoCarteDAO().doRetriveActualState(dati.getNumCarta(), TipiCarte.CREDITO);

                RelStatoCarta rel= MagnusDAO.getInstance().getRelStatoCarteDAO().doRetriveActualRel(dati.getNumCarta(), TipiCarte.CREDITO);
                rel.setDataFineStato(LocalDate.now().toString());

                MagnusDAO.getInstance().getRelStatoCarteDAO().saveOrUpdate(rel, TipiCarte.CREDITO);
                if (statoCarta.getValoreStato().equals("attivo")) {
                    statoCarta = MagnusDAO.getInstance().getStatoDAO().doRetriveByAttribute(ValoriStato.SOSPESO);
                    not= new Notifiche(PresetNotifiche.NOTIFICA_SOSPENSIONE_CARTA_PT_I+"credito"+PresetNotifiche.NOTIFICA_SOSPENSIONE_CARTA_PT_II+LocalDate.now(), false);
                }else {
                    statoCarta = MagnusDAO.getInstance().getStatoDAO().doRetriveByAttribute(ValoriStato.ATTIVO);
                    not= new Notifiche(PresetNotifiche.NOTIFICA_ATTIVAZIONE_CARTA_PT_I+"credito"+PresetNotifiche.NOTIFICA_ATTIVAZIONE_CARTA_PT_II+LocalDate.now(), false);
                }
                rel= new RelStatoCarta(LocalDate.now().toString(), statoCarta, carta);
                MagnusDAO.getInstance().getRelStatoCarteDAO().saveOrUpdate(rel, TipiCarte.CREDITO);

                MagnusDAO.getInstance().getNotificheDAO().saveOrUpdate(not, cc.getNumCC());
                cc.addNotifica(not);

                result = false;
            }else {
                MagnusDAO.getInstance().getRelStatoCarteDAO().delete(carta.getNumCarta(), TipiCarte.CREDITO);
                MagnusDAO.getInstance().getCarteDAO().delete(carta, TipiCarte.CREDITO);

                not= new Notifiche(PresetNotifiche.NOTIFICA_CANCELLAZIONE_CARTA_PT_I+"credito"+PresetNotifiche.NOTIFICA_CANCELLAZIONE_CARTA_PT_II+LocalDate.now(), false);
                MagnusDAO.getInstance().getNotificheDAO().saveOrUpdate(not, cc.getNumCC());
                cc.addNotifica(not);
                result= true;
            }

        }
        else {
            carta = MagnusDAO.getInstance().getCarteDAO().doRetriveByKey(dati.getNumCarta(), TipiCarte.DEBITO, true);

            if (dati.getStato() == 1) {
                statoCarta = MagnusDAO.getInstance().getRelStatoCarteDAO().doRetriveActualState(dati.getNumCarta(), TipiCarte.DEBITO);

                RelStatoCarta rel= MagnusDAO.getInstance().getRelStatoCarteDAO().doRetriveActualRel(dati.getNumCarta(), TipiCarte.DEBITO);
                rel.setDataFineStato(LocalDate.now().toString());

                MagnusDAO.getInstance().getRelStatoCarteDAO().saveOrUpdate(rel, TipiCarte.DEBITO);
                if (statoCarta.getValoreStato().equals("attivo")) {
                    statoCarta= MagnusDAO.getInstance().getStatoDAO().doRetriveByAttribute(ValoriStato.SOSPESO);
                    not= new Notifiche(PresetNotifiche.NOTIFICA_SOSPENSIONE_CARTA_PT_I+"debito"+PresetNotifiche.NOTIFICA_SOSPENSIONE_CARTA_PT_II+LocalDate.now(), false);
                }else{
                    statoCarta= MagnusDAO.getInstance().getStatoDAO().doRetriveByAttribute(ValoriStato.ATTIVO);
                    not= new Notifiche(PresetNotifiche.NOTIFICA_ATTIVAZIONE_CARTA_PT_I+"debito"+PresetNotifiche.NOTIFICA_ATTIVAZIONE_CARTA_PT_II+LocalDate.now(), false);
                }
                rel= new RelStatoCarta(LocalDate.now().toString(), statoCarta, carta);
                MagnusDAO.getInstance().getRelStatoCarteDAO().saveOrUpdate(rel, TipiCarte.DEBITO);

                MagnusDAO.getInstance().getNotificheDAO().saveOrUpdate(not, cc.getNumCC());
                cc.addNotifica(not);

                result= false;
            } else {
                MagnusDAO.getInstance().getRelStatoCarteDAO().delete(carta.getNumCarta(), TipiCarte.DEBITO);
                MagnusDAO.getInstance().getCarteDAO().delete(carta, TipiCarte.DEBITO);

                not= new Notifiche(PresetNotifiche.NOTIFICA_CANCELLAZIONE_CARTA_PT_I+"debito"+PresetNotifiche.NOTIFICA_CANCELLAZIONE_CARTA_PT_II+LocalDate.now(), false);
                MagnusDAO.getInstance().getNotificheDAO().saveOrUpdate(not, cc.getNumCC());
                cc.addNotifica(not);
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

    @GetMapping("/prendiNumeroCarte")
    public int[] prendiNumeroCarte(HttpServletRequest request, @RequestParam("IDSession") String idSession) {
        int[] numeroCarte= new int[2];

        HttpSession session = (HttpSession) request.getServletContext().getAttribute(idSession);
        ContoCorrente cc= (ContoCorrente)session.getAttribute("Conto");

        Vector<Carte> carteCredito= MagnusDAO.getInstance().getCarteDAO().doRetriveAllCreditForCC(cc.getNumCC());
        Vector<Carte> carteDebito= MagnusDAO.getInstance().getCarteDAO().doRetriveAllDebitForCC(cc.getNumCC());

        if(carteCredito!=null)
            numeroCarte[0]= carteCredito.size();
        if(carteDebito!=null)
            numeroCarte[1]= carteDebito.size();

        return numeroCarte;
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
