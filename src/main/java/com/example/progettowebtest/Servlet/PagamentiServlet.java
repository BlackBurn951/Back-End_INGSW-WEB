package com.example.progettowebtest.Servlet;


import com.example.progettowebtest.ClassiRequest.DatiBollettino;
import com.example.progettowebtest.ClassiRequest.DatiBonificoInter;
import com.example.progettowebtest.ClassiRequest.DatiBonificoSepa;
import com.example.progettowebtest.DAO.MagnusDAO;
import com.example.progettowebtest.Model.Carte.Carte;
import com.example.progettowebtest.Model.Carte.TipiCarte;
import com.example.progettowebtest.Model.ContoCorrente.ContoCorrente;
import com.example.progettowebtest.Model.ContoCorrente.Notifiche;
import com.example.progettowebtest.Model.ContoCorrente.PresetNotifiche;
import com.example.progettowebtest.Model.Proxy.Transazione;
import com.example.progettowebtest.Model.Transazioni.Bollettino;
import com.example.progettowebtest.Model.Transazioni.BonificoInter;
import com.example.progettowebtest.Model.Transazioni.BonificoSepa;
import com.example.progettowebtest.Model.Transazioni.TipologiaBollettino;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Vector;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PagamentiServlet {

    @GetMapping("/checkStatoConto")
    public boolean checkStatus(HttpServletRequest request, @RequestParam("IDSession") String idSess) {
        HttpSession session= (HttpSession) request.getServletContext().getAttribute(idSess);
        ContoCorrente cc= (ContoCorrente) session.getAttribute("Conto");
        String stato = cc.getStatoConto().getValoreStato();

        return stato.equals("attivo");
    }
    @GetMapping("/checkPin")
    public String checkPin(HttpServletRequest request, @RequestParam("pinSend") String pinSend, @RequestParam("IDSession") String idSess) {
        HttpSession session= (HttpSession) request.getServletContext().getAttribute(idSess);
        ContoCorrente cc= (ContoCorrente) session.getAttribute("Conto");
        String pin= cc.getPin();

        if(BCrypt.checkpw(pinSend, pin))
            return "PIN corretto";
        else
            return "PIN errato";
    }


    //FARE CALCOLO DEL FIDO
    @PostMapping("/doBollettino")
    public int doBollettino(HttpServletRequest request, @RequestParam("IDSession") String idSess, @RequestBody DatiBollettino dati) {
        int result= 2;  //0 trans comn il conto, 1 soldi dalla carta, 2 trans negata
        boolean esito= false;

        HttpSession session= (HttpSession) request.getServletContext().getAttribute(idSess);
        ContoCorrente cc= (ContoCorrente) session.getAttribute("Conto");

        Vector<Carte> carteCredito= MagnusDAO.getInstance().getCarteDAO().doRetriveAllCreditForCC(cc.getNumCC());

        if(cc.getSaldo()>=(dati.getImporto()+1.0)) {
            cc.setSaldo(cc.getSaldo() - (dati.getImporto()+1.0));
            if(MagnusDAO.getInstance().getContoCorrenteDAO().saveOrUpdate(cc, false)) {
                Notifiche not= new Notifiche(PresetNotifiche.NOTIFICA_BOLLETTINO+LocalDate.now(), false);
                MagnusDAO.getInstance().getNotificheDAO().saveOrUpdate(not, cc.getNumCC());
                cc.addNotifica(not);
                result = 0;
                esito= true;
            }
        }
        else if(carteCredito!=null) {
            for(Carte cr: carteCredito) {
                if(cr.getFido()>=(dati.getImporto()+1.0) && cr.getStatoCarta().getValoreStato().equals("attivo")) {
                    cr.setFido(cr.getFido()-(dati.getImporto()+1.0));
                    if(MagnusDAO.getInstance().getCarteDAO().saveOrUpdate(cr, TipiCarte.CREDITO)) {
                        Notifiche not= new Notifiche(PresetNotifiche.NOTIFICA_BOLLETTINO+LocalDate.now(), false);
                        MagnusDAO.getInstance().getNotificheDAO().saveOrUpdate(not, cc.getNumCC());
                        cc.addNotifica(not);
                        result = 1;
                        esito= true;
                    }
                    break;
                }
            }
            if(!esito) {
                Notifiche not= new Notifiche(PresetNotifiche.NOTIFICA_TRANSNEGATA+LocalDate.now(), false);
                MagnusDAO.getInstance().getNotificheDAO().saveOrUpdate(not, cc.getNumCC());
                cc.addNotifica(not);
            }
        }else {
            Notifiche not= new Notifiche(PresetNotifiche.NOTIFICA_TRANSNEGATA+LocalDate.now(), false);
            MagnusDAO.getInstance().getNotificheDAO().saveOrUpdate(not, cc.getNumCC());
            cc.addNotifica(not);
        }

        TipologiaBollettino tipo= MagnusDAO.getInstance().getTipologiaBollettinoDAO().doRetriveByAttribute(dati.getTipologiaBollettino());
        Bollettino bol= new Bollettino(LocalDate.now().toString(), 1.0, esito, dati.getImporto(), dati.getCausale(), cc.getNumCC(), tipo);

        MagnusDAO.getInstance().getBollettinoDAO().saveOrUpdate(bol, cc.getNumCC());

        Transazione proxy= MagnusDAO.getInstance().getBollettinoDAO().doRetriveByKey(MagnusDAO.getInstance().getBollettinoDAO().retriveLastId(), false);
        if(proxy!=null) {
            cc.addTransazione(proxy);
        }
        return result;
    }

    @PostMapping("/doBonificoSepa")
    public int doBonificoSepa(HttpServletRequest request, @RequestParam("IDSession") String idSess, @RequestBody DatiBonificoSepa dati) {
        int result = 2;
        boolean esito= false;

        HttpSession session= (HttpSession) request.getServletContext().getAttribute(idSess);
        ContoCorrente cc= (ContoCorrente) session.getAttribute("Conto");

        Vector<Carte> carteCredito= MagnusDAO.getInstance().getCarteDAO().doRetriveAllCreditForCC(cc.getNumCC());

        if(cc.getSaldo()>=(dati.getImportoSepa()+1.0)) {
            cc.setSaldo(cc.getSaldo() - (dati.getImportoSepa() + 1.0));
            if(MagnusDAO.getInstance().getContoCorrenteDAO().saveOrUpdate(cc, false)) {
                Notifiche not= new Notifiche(PresetNotifiche.NOTIFICA_BONIFICOSEPA+LocalDate.now(), false);
                MagnusDAO.getInstance().getNotificheDAO().saveOrUpdate(not, cc.getNumCC());
                cc.addNotifica(not);
                result = 0;
                esito= true;

            }
        }else if(carteCredito!=null) {
            for(Carte cr: carteCredito) {
                if(cr.getFido()>=(dati.getImportoSepa()+1.0) && cr.getStatoCarta().getValoreStato().equals("attivo")) {
                    cr.setFido(cr.getFido()-(dati.getImportoSepa()+1.0));
                    if(MagnusDAO.getInstance().getCarteDAO().saveOrUpdate(cr, TipiCarte.CREDITO)) {
                        Notifiche not= new Notifiche(PresetNotifiche.NOTIFICA_BONIFICOSEPA+LocalDate.now(), false);
                        MagnusDAO.getInstance().getNotificheDAO().saveOrUpdate(not, cc.getNumCC());
                        cc.addNotifica(not);
                        result = 1;
                        esito= true;

                    }
                    break;
                }
            }
            if(!esito) {
                Notifiche not= new Notifiche(PresetNotifiche.NOTIFICA_TRANSNEGATA+LocalDate.now(), false);
                MagnusDAO.getInstance().getNotificheDAO().saveOrUpdate(not, cc.getNumCC());
                cc.addNotifica(not);
            }
        }else {
            Notifiche not= new Notifiche(PresetNotifiche.NOTIFICA_TRANSNEGATA+LocalDate.now(), false);
            MagnusDAO.getInstance().getNotificheDAO().saveOrUpdate(not, cc.getNumCC());
            cc.addNotifica(not);
        }


        BonificoSepa sepa= new BonificoSepa(LocalDate.now().toString(), 1.0, esito, dati.getNomeBeneficiario(), dati.getCognomeBeneficiario(), dati.getImportoSepa(), dati.getCausaleSepa(), dati.getIbanDestinatarioSepa());

        MagnusDAO.getInstance().getBonificoSepaDAO().saveOrUpdate(sepa, cc.getNumCC());

        Transazione proxy= MagnusDAO.getInstance().getBonificoSepaDAO().doRetriveByKey(MagnusDAO.getInstance().getBonificoSepaDAO().retriveLastId(), false);
        if(proxy!=null) {
            cc.addTransazione(proxy);
        }

        return result;
    }

    @PostMapping("/doBonificoInt")
    public int doBonificoInt(HttpServletRequest request, @RequestParam("IDSession") String idSess, @RequestBody DatiBonificoInter dati) {
        int result = 2;
        boolean esito= false;

        HttpSession session= (HttpSession) request.getServletContext().getAttribute(idSess);
        ContoCorrente cc= (ContoCorrente) session.getAttribute("Conto");

        Vector<Carte> carteCredito= MagnusDAO.getInstance().getCarteDAO().doRetriveAllCreditForCC(cc.getNumCC());

        if(cc.getSaldo()>=(dati.getImportoI()+1.0)) {
            cc.setSaldo(cc.getSaldo() - (dati.getImportoI() + 1.0));
            if(MagnusDAO.getInstance().getContoCorrenteDAO().saveOrUpdate(cc, false)) {
                Notifiche not= new Notifiche(PresetNotifiche.NOTIFICA_BONIFICOINTER+LocalDate.now(), false);
                MagnusDAO.getInstance().getNotificheDAO().saveOrUpdate(not, cc.getNumCC());
                cc.addNotifica(not);
                result = 0;
                esito= true;
            }
        }else if(carteCredito!=null) {
            for(Carte cr: carteCredito) {
                if(cr.getFido()>=(dati.getImportoI()+1.0) && cr.getStatoCarta().getValoreStato().equals("attivo")) {
                    cr.setFido(cr.getFido()-(dati.getImportoI()+1.0));
                    if(MagnusDAO.getInstance().getCarteDAO().saveOrUpdate(cr, TipiCarte.CREDITO)) {
                        Notifiche not= new Notifiche(PresetNotifiche.NOTIFICA_BONIFICOINTER+LocalDate.now(), false);
                        MagnusDAO.getInstance().getNotificheDAO().saveOrUpdate(not, cc.getNumCC());
                        cc.addNotifica(not);
                        result = 1;
                        esito= true;
                    }
                    break;
                }
            }
            if(!esito) {
                Notifiche not= new Notifiche(PresetNotifiche.NOTIFICA_TRANSNEGATA+LocalDate.now(), false);
                MagnusDAO.getInstance().getNotificheDAO().saveOrUpdate(not, cc.getNumCC());
                cc.addNotifica(not);
            }
        }else {
            Notifiche not= new Notifiche(PresetNotifiche.NOTIFICA_TRANSNEGATA+LocalDate.now(), false);
            MagnusDAO.getInstance().getNotificheDAO().saveOrUpdate(not, cc.getNumCC());
            cc.addNotifica(not);
        }

        BonificoInter inter = new BonificoInter(LocalDate.now().toString(), 1.0, esito, dati.getNomeBeneficiarioI(), dati.getCognomeBeneficiarioI(), dati.getImportoI(), dati.getCausaleI(), dati.getIbanDestinatarioI(), dati.getValuta(), dati.getPaeseDest());

        MagnusDAO.getInstance().getBonificoInterDAO().saveOrUpdate(inter, cc.getNumCC());

        Transazione proxy= MagnusDAO.getInstance().getBonificoInterDAO().doRetriveByKey(MagnusDAO.getInstance().getBonificoInterDAO().retriveLastId(), false);
        if(proxy!=null) {
            cc.addTransazione(proxy);
        }
        return result;
    }
}
