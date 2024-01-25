package com.example.progettowebtest.Servlet;

import com.example.progettowebtest.ClassiRequest.CambioEmail;
import com.example.progettowebtest.ClassiRequest.CambioPassword;
import com.example.progettowebtest.ClassiRequest.IdentificativiUtente;
import com.example.progettowebtest.DAO.MagnusDAO;
import com.example.progettowebtest.Model.Carte.Carte;
import com.example.progettowebtest.Model.Carte.RelStatoCarta;
import com.example.progettowebtest.Model.Carte.TipiCarte;
import com.example.progettowebtest.Model.ContoCorrente.ContoCorrente;
import com.example.progettowebtest.Model.ContoCorrente.Notifiche;
import com.example.progettowebtest.Model.ContoCorrente.PresetNotifiche;
import com.example.progettowebtest.Model.ContoCorrente.RelStatoConto;
import com.example.progettowebtest.Model.Proxy.Transazione;
import com.example.progettowebtest.Model.Stato;
import com.example.progettowebtest.Model.Utente_Documenti.Utente;
import com.example.progettowebtest.Model.ValoriStato;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Vector;

@RestController
@CrossOrigin(origins = "http://localhost:4200",  exposedHeaders = "Session-ID")
public class GestioneContoServlet {

    @PostMapping("/changeEmail")
    public boolean cambiaEmail(HttpServletRequest request, @RequestParam("IDSession") String idSession, @RequestBody CambioEmail dati) {
        HttpSession session= (HttpSession)request.getServletContext().getAttribute(idSession);

        Utente ut= (Utente)session.getAttribute("Utente");
        Utente contr= MagnusDAO.getInstance().getUtenteDAO().doRetriveByKey(dati.getEmail(), IdentificativiUtente.EMAIL);

        if(contr==null) {
            ut.setEmail(dati.getEmail());
            if(MagnusDAO.getInstance().getUtenteDAO().saveOrUpdate(ut))
                return true;
        }
        return false;
    }

    @PostMapping("/cambiaPass")
    public boolean cambiaPass(HttpServletRequest request, @RequestParam("IDSession") String idSession, @RequestParam("password") String cambio) {
        HttpSession session= (HttpSession)request.getServletContext().getAttribute(idSession);

        Utente ut= (Utente)session.getAttribute("Utente");
        ut.setPassword(cambio);
        return MagnusDAO.getInstance().getUtenteDAO().saveOrUpdate(ut);
    }

    @GetMapping("checkPass")
    public boolean checkPsw(HttpServletRequest request, @RequestParam("IDSession") String idSession, @RequestParam("password") String psw) {
        HttpSession session= (HttpSession)request.getServletContext().getAttribute(idSession);

        Utente ut= (Utente)session.getAttribute("Utente");
        return BCrypt.checkpw(psw, ut.getPassword());
    }

    @PostMapping("/cambiaStatoConto")
    public boolean cambiaStatoConto(HttpServletRequest request, @RequestParam("IDSession") String idSession, @RequestParam("stato") int stato){
        boolean result;  //True -> eliminato  False -> attivata/disattivata

        HttpSession session= (HttpSession)request.getServletContext().getAttribute(idSession);

        ContoCorrente cc= (ContoCorrente)session.getAttribute("Conto");
        Stato statoConto= cc.getStatoConto();
        Notifiche not;

        if (stato == 1) {
            RelStatoConto rel= MagnusDAO.getInstance().getRelStatoContoDAO().doRetriveActualRel(cc.getNumCC());
            rel.setDataFineStato(LocalDate.now().toString());

            MagnusDAO.getInstance().getRelStatoContoDAO().saveOrUpdate(rel);
            if (statoConto.getValoreStato().equals("attivo")) {
                statoConto= MagnusDAO.getInstance().getStatoDAO().doRetriveByAttribute(ValoriStato.SOSPESO);
                not= new Notifiche(PresetNotifiche.NOTIFICA_SOSPENSIONE_CONTO+LocalDate.now(), false);
            }else{
                statoConto= MagnusDAO.getInstance().getStatoDAO().doRetriveByAttribute(ValoriStato.ATTIVO);
                not= new Notifiche(PresetNotifiche.NOTIFICA_ATTIVAZIONE_CONTO+LocalDate.now(), false);
            }
            rel= new RelStatoConto(LocalDate.now().toString(), statoConto, cc);

            MagnusDAO.getInstance().getRelStatoContoDAO().saveOrUpdate(rel);

            MagnusDAO.getInstance().getNotificheDAO().saveOrUpdate(not, cc.getNumCC());
            cc.addNotifica(not);

            cc.setStatoConto(statoConto);

            result= false;
        }else {
            MagnusDAO.getInstance().getRelStatoContoDAO().delete(cc.getNumCC());

            Vector<Carte> carteConto= MagnusDAO.getInstance().getCarteDAO().doRetriveAllForCC(cc.getNumCC());
            for(Carte cr: carteConto) {
                if(cr.getTipoCarta()==TipiCarte.CREDITO) {
                    MagnusDAO.getInstance().getRelStatoCarteDAO().delete(cr.getNumCarta(), TipiCarte.CREDITO);
                    MagnusDAO.getInstance().getCarteDAO().delete(cr, TipiCarte.CREDITO);
                }
                else {
                    MagnusDAO.getInstance().getRelStatoCarteDAO().delete(cr.getNumCarta(), TipiCarte.DEBITO);
                    MagnusDAO.getInstance().getCarteDAO().delete(cr, TipiCarte.DEBITO);
                }
            }

            Vector<Transazione> trans= MagnusDAO.getInstance().getBollettinoDAO().doRetriveAllForCC(cc.getNumCC());
            for(Transazione tr: trans) {
                MagnusDAO.getInstance().getBollettinoDAO().delete(tr.getId());
            }

            trans= MagnusDAO.getInstance().getBonificoSepaDAO().doRetriveAllForCC(cc.getNumCC());
            for(Transazione tr: trans) {
                MagnusDAO.getInstance().getBonificoSepaDAO().delete(tr.getId());
            }

            trans= MagnusDAO.getInstance().getBonificoInterDAO().doRetriveAllForCC(cc.getNumCC());
            for(Transazione tr: trans) {
                MagnusDAO.getInstance().getBonificoInterDAO().delete(tr.getId());
            }

            trans= MagnusDAO.getInstance().getDepositoDAO().doRetriveAllForCC(cc.getNumCC());
            for(Transazione tr: trans) {
                MagnusDAO.getInstance().getDepositoDAO().delete(tr.getId());
            }

            trans= MagnusDAO.getInstance().getPrelievoDAO().doRetriveAllForCC(cc.getNumCC());
            for(Transazione tr: trans) {
                MagnusDAO.getInstance().getPrelievoDAO().delete(tr.getId());
            }

            MagnusDAO.getInstance().getContoCorrenteDAO().delete(cc.getNumCC());

            MagnusDAO.getInstance().getUtenteDAO().delete((Utente) session.getAttribute("Utente"));

            MagnusDAO.getInstance().getIndirizzoDAO().delete(cc.getIndFatturazione());
            result= true;
        }
        return result;
    }

}
