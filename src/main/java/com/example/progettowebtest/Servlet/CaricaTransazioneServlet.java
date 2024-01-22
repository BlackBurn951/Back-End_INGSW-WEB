package com.example.progettowebtest.Servlet;

import com.example.progettowebtest.Model.ContoCorrente.ContoCorrente;
import com.example.progettowebtest.Model.Proxy.Transazione;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Vector;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CaricaTransazioneServlet {

    @GetMapping("/visualizzaTrans")
    public List<String> visualizzaTrans(HttpServletRequest request, @RequestParam("IDSession") String idSession, @RequestParam("idTrans") int id, @RequestParam("tipoTrans") String tipoTrans) {
        Vector<String> result= new Vector<>();

        HttpSession session= (HttpSession)request.getServletContext().getAttribute(idSession);
        ContoCorrente cc= (ContoCorrente)session.getAttribute("Conto");

        for(Transazione tr: cc.getMovimenti()) {
            if(tr.getId()==id && tr.getTipoTrans().equals(tipoTrans)){
                switch (tr.getTipoTrans()) {
                    case "Bollettino":

                        break;
                    case "BonificoInter":
                        break;
                    case "BonificoSepa":
                        break;
                    case "Deposito":
                        break;
                    case "Prelievo":
                        break;
                }
            }
        }
    }
}
