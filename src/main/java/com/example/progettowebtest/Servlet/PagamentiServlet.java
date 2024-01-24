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
    public boolean doBollettino(HttpServletRequest request, @RequestParam("IDSession") String idSess, @RequestBody DatiBollettino dati) {
        boolean result= false;

        HttpSession session= (HttpSession) request.getServletContext().getAttribute(idSess);
        ContoCorrente cc= (ContoCorrente) session.getAttribute("Conto");

        Vector<Carte> carteCredito= MagnusDAO.getInstance().getCarteDAO().doRetriveAllCreditForCC(cc.getNumCC());

        if(cc.getSaldo()>=(dati.getImporto()+1.0)) {
            cc.setSaldo(cc.getSaldo() - (dati.getImporto()+1.0));
            if(MagnusDAO.getInstance().getContoCorrenteDAO().saveOrUpdate(cc, false)) {
                Notifiche not= new Notifiche(PresetNotifiche.NOTIFICA_BOLLETTINO, false);
                MagnusDAO.getInstance().getNotificheDAO().saveOrUpdate(not, cc.getNumCC());
                cc.addNotifica(not);
                result = true;
            }
        }
        else if(carteCredito!=null) {
            for(Carte cr: carteCredito) {
                if(cr.getFido()>=(dati.getImporto()+1.0)) {
                    cr.setFido(cr.getFido()-(dati.getImporto()+1.0));
                    if(MagnusDAO.getInstance().getCarteDAO().saveOrUpdate(cr, TipiCarte.CREDITO)) {
                        Notifiche not= new Notifiche(PresetNotifiche.NOTIFICA_BOLLETTINO, false);
                        MagnusDAO.getInstance().getNotificheDAO().saveOrUpdate(not, cc.getNumCC());
                        cc.addNotifica(not);
                        result = true;
                    }
                    break;
                }
            }
        }
        else
            result= false;

        TipologiaBollettino tipo= MagnusDAO.getInstance().getTipologiaBollettinoDAO().doRetriveByAttribute(dati.getTipologiaBollettino());
        Bollettino bol= new Bollettino(LocalDate.now().toString(), 1.0, result, dati.getImporto(), dati.getCausale(), cc.getNumCC(), tipo);

        MagnusDAO.getInstance().getBollettinoDAO().saveOrUpdate(bol, cc.getNumCC());

        Transazione proxy= MagnusDAO.getInstance().getBollettinoDAO().doRetriveByKey(bol.getId(), false);
        if(proxy!=null)
            cc.addTransazione(proxy);

        return result;
    }

    @PostMapping("/doBonificoSepa")
    public boolean doBonificoSepa(HttpServletRequest request, @RequestParam("IDSession") String idSess, @RequestBody DatiBonificoSepa dati) {
        boolean result= false;

        HttpSession session= (HttpSession) request.getServletContext().getAttribute(idSess);
        ContoCorrente cc= (ContoCorrente) session.getAttribute("Conto");

        Vector<Carte> carteCredito= MagnusDAO.getInstance().getCarteDAO().doRetriveAllCreditForCC(cc.getNumCC());

        if(cc.getSaldo()>=(dati.getImportoSepa()+1.0)) {
            cc.setSaldo(cc.getSaldo() - (dati.getImportoSepa() + 1.0));
            if(MagnusDAO.getInstance().getContoCorrenteDAO().saveOrUpdate(cc, false)) {
                Notifiche not= new Notifiche(PresetNotifiche.NOTIFICA_BONIFICOSEPA, false);
                MagnusDAO.getInstance().getNotificheDAO().saveOrUpdate(not, cc.getNumCC());
                cc.addNotifica(not);
                result = true;
            }
        }else if(carteCredito!=null) {
            for(Carte cr: carteCredito) {
                if(cr.getFido()>=(dati.getImportoSepa()+1.0)) {
                    cr.setFido(cr.getFido()-(dati.getImportoSepa()+1.0));
                    if(MagnusDAO.getInstance().getCarteDAO().saveOrUpdate(cr, TipiCarte.CREDITO)) {
                        Notifiche not= new Notifiche(PresetNotifiche.NOTIFICA_BONIFICOSEPA, false);
                        MagnusDAO.getInstance().getNotificheDAO().saveOrUpdate(not, cc.getNumCC());
                        cc.addNotifica(not);
                        result = true;
                    }
                    break;
                }
            }
        }
        else
            result= false;


        BonificoSepa sepa= new BonificoSepa(LocalDate.now().toString(), 1.0, result, dati.getNomeBeneficiario(), dati.getCognomeBeneficiario(), dati.getImportoSepa(), dati.getCausaleSepa(), dati.getIbanDestinatarioSepa());

        MagnusDAO.getInstance().getBonificoSepaDAO().saveOrUpdate(sepa, cc.getNumCC());

        Transazione proxy= MagnusDAO.getInstance().getBonificoSepaDAO().doRetriveByKey(sepa.getId(), false);
        if(proxy!=null)
            cc.addTransazione(proxy);

        return result;
    }

    @PostMapping("/doBonificoInt")
    public boolean doBonificoInt(HttpServletRequest request, @RequestParam("IDSession") String idSess, @RequestBody DatiBonificoInter dati) {
        boolean result = false;

        HttpSession session= (HttpSession) request.getServletContext().getAttribute(idSess);
        ContoCorrente cc= (ContoCorrente) session.getAttribute("Conto");

        Vector<Carte> carteCredito= MagnusDAO.getInstance().getCarteDAO().doRetriveAllCreditForCC(cc.getNumCC());

        if(cc.getSaldo()>=(dati.getImportoI()+1.0)) {
            cc.setSaldo(cc.getSaldo() - (dati.getImportoI() + 1.0));
            if(MagnusDAO.getInstance().getContoCorrenteDAO().saveOrUpdate(cc, false)) {
                Notifiche not= new Notifiche(PresetNotifiche.NOTIFICA_BONIFICOINTER, false);
                MagnusDAO.getInstance().getNotificheDAO().saveOrUpdate(not, cc.getNumCC());
                cc.addNotifica(not);
                result = true;
            }
        }else if(carteCredito!=null) {
            for(Carte cr: carteCredito) {
                if(cr.getFido()>=(dati.getImportoI()+1.0)) {
                    cr.setFido(cr.getFido()-(dati.getImportoI()+1.0));
                    if(MagnusDAO.getInstance().getCarteDAO().saveOrUpdate(cr, TipiCarte.CREDITO)) {
                        Notifiche not= new Notifiche(PresetNotifiche.NOTIFICA_BONIFICOINTER, false);
                        MagnusDAO.getInstance().getNotificheDAO().saveOrUpdate(not, cc.getNumCC());
                        cc.addNotifica(not);
                        result = true;
                    }
                    break;
                }
            }
        }
        else
            result= false;

        BonificoInter inter = new BonificoInter(LocalDate.now().toString(), 1.0, result, dati.getNomeBeneficiarioI(), dati.getCognomeBeneficiarioI(), dati.getImportoI(), dati.getCausaleI(), dati.getIbanDestinatarioI(), dati.getValuta(), dati.getPaeseDest());

        MagnusDAO.getInstance().getBonificoInterDAO().saveOrUpdate(inter, cc.getNumCC());

        Transazione proxy= MagnusDAO.getInstance().getBonificoInterDAO().doRetriveByKey(inter.getId(), false);
        if(proxy!=null)
            cc.addTransazione(proxy);

        return result;
    }
}
