package com.example.progettowebtest.Servlet;

import com.example.progettowebtest.ClassiRequest.IdentificativiUtente;
import com.example.progettowebtest.DAO.MagnusDAO;
import com.example.progettowebtest.Model.Carte.Carte;
import com.example.progettowebtest.Model.Carte.RelStatoCarta;
import com.example.progettowebtest.Model.Carte.TipiCarte;
import com.example.progettowebtest.Model.ContoCorrente.ContoCorrente;
import com.example.progettowebtest.Model.ContoCorrente.RelStatoConto;
import com.example.progettowebtest.Model.Proxy.Transazione;
import com.example.progettowebtest.Model.Stato;
import com.example.progettowebtest.Model.Utente_Documenti.Utente;
import com.example.progettowebtest.Model.ValoriStato;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Vector;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class GestioneContoServlet {

    @PostMapping("/changeEmail")
    public boolean cambiaEmail(HttpServletRequest request, @RequestParam("IDSession") String idSession, @RequestParam("email") String email) {
        HttpSession session= (HttpSession)request.getServletContext().getAttribute(idSession);

        Utente ut= (Utente)session.getAttribute("Utente");
        Utente contr= MagnusDAO.getInstance().getUtenteDAO().doRetriveByKey(email, IdentificativiUtente.EMAIL);

        if(contr==null) {
            ut.setEmail(email);
            if(MagnusDAO.getInstance().getUtenteDAO().saveOrUpdate(ut))
                return true;
        }
        return false;
    }

    @PostMapping("/cambiaStatoConto")
    public boolean cambiaStatoConto(HttpServletRequest request, @RequestParam("IDSession") String idSession, @RequestParam("stato") int stato){
        boolean result;  //True -> eliminato  False -> attivata/disattivata

        HttpSession session= (HttpSession)request.getServletContext().getAttribute(idSession);

        ContoCorrente cc= (ContoCorrente)session.getAttribute("Conto");
        Stato statoConto= cc.getStatoConto();

        if (stato == 1) {
            RelStatoConto rel= MagnusDAO.getInstance().getRelStatoContoDAO().doRetriveActualRel(cc.getNumCC());
            rel.setDataFineStato(LocalDate.now().toString());

            MagnusDAO.getInstance().getRelStatoContoDAO().saveOrUpdate(rel);
            if (statoConto.getValoreStato().equals("attivo")) {
                statoConto= MagnusDAO.getInstance().getStatoDAO().doRetriveByAttribute(ValoriStato.SOSPESO);
                rel= new RelStatoConto(LocalDate.now().toString(), statoConto, cc);

                MagnusDAO.getInstance().getRelStatoContoDAO().saveOrUpdate(rel);

                result = false;
            }else{
                statoConto= MagnusDAO.getInstance().getStatoDAO().doRetriveByAttribute(ValoriStato.ATTIVO);
                rel= new RelStatoConto(LocalDate.now().toString(), statoConto, cc);

                MagnusDAO.getInstance().getRelStatoContoDAO().saveOrUpdate(rel);

                result = false;
            }

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
            eliminaTransazione(trans);

            trans= MagnusDAO.getInstance().getBonificoSepaDAO().doRetriveAllForCC(cc.getNumCC());
            eliminaTransazione(trans);

            trans= MagnusDAO.getInstance().getBonificoInterDAO().doRetriveAllForCC(cc.getNumCC());
            eliminaTransazione(trans);

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


    //Metodi di servizio
    private void eliminaTransazione(Vector<Transazione> trans) {
        for(Transazione tr: trans) {
            MagnusDAO.getInstance().getBollettinoDAO().delete(tr.getId());
        }
    }
}
