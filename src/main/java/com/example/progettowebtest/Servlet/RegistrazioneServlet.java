package com.example.progettowebtest.Servlet;

import com.example.progettowebtest.ClassiRequest.DatiControlloUtente;
import com.example.progettowebtest.ClassiRequest.DatiRegistrazione;
import com.example.progettowebtest.DAO.Utente_Documenti.UtenteDAO;
import com.example.progettowebtest.DAO.Utente_Documenti.UtenteDAOImpl;
import com.example.progettowebtest.ClassiRequest.IdentificativiUtente;
import jakarta.servlet.http.*;
import org.mortbay.util.ajax.JSON;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:4200", exposedHeaders = "Session-ID")
public class RegistrazioneServlet extends HttpServlet {
    private UtenteDAO utenteDAO= UtenteDAOImpl.getInstance();

    //DA SPOSTARE
    @PostMapping("/emailCheck")
    public int emailCheck(@RequestBody DatiControlloUtente dati) {
        int result= 2;

        if(utenteDAO.doRetriveByKey(dati.getEmail(), IdentificativiUtente.EMAIL)!=null)
            result= 0;
        else if(utenteDAO.doRetriveByKey(dati.getCf(), IdentificativiUtente.CF)!=null)
            result= 1;

        return result;
    }


    //DA CONTROLLARE IL CONTO DEL MINUTAGGIO
    @GetMapping("/checkOTP")
    public String checkOTP(HttpServletRequest request, @RequestParam("otpSend") String otpSend, @RequestParam("IdSession") String idSess) {
        String response;

        HttpSession session= (HttpSession) request.getServletContext().getAttribute(idSess);
        long creationTime = session.getCreationTime(); // Tempo di creazione della sessione in millisecondi
        long currentTime = System.currentTimeMillis(); // Tempo corrente in millisecondi
        long tenMinutesInMillis = 10 * 60 * 1000; // 10 minuti in millisecondi
        if(session!=null && (currentTime - creationTime < tenMinutesInMillis)) {
            if(session.getAttribute("control").equals(otpSend)) {
                response = "OTP corretto";
                System.out.println(response);
            }
            else {
                response = "OTP errato";
                System.out.println(response);
            }
        }
        else if(session!=null && (currentTime - creationTime >= tenMinutesInMillis))
            response= "Sessione scaduta";
        else {
            System.out.println("Server: sessione non trovata!!!");
            response= "Sessione non esistente!!!";
        }

        return response;
    }

    @PostMapping("/insertUser")
    public boolean insertUser(@RequestBody DatiRegistrazione dati) {
        System.out.println(dati.getCf()+" "+dati.getCognomeDocumento()+" "+dati.getCittaRes()+" "+dati.getReddito());
        return true;
    }
}
