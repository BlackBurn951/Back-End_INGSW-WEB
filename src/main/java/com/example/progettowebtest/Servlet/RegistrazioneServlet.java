package com.example.progettowebtest.Servlet;

import com.example.progettowebtest.ClassiRequest.DatiControlloUtente;
import com.example.progettowebtest.ClassiRequest.DatiRegistrazione;
import com.example.progettowebtest.DAO.Utente_Documenti.UtenteDAO;
import com.example.progettowebtest.DAO.Utente_Documenti.UtenteDAOImpl;
import com.example.progettowebtest.ClassiRequest.IdentificativiUtente;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@RestController
public class RegistrazioneServlet extends HttpServlet {
    private UtenteDAO utenteDAO= UtenteDAOImpl.getInstance();

    @GetMapping("/emailCheck")
    public int emailCheck(@RequestBody DatiControlloUtente dati) {
        int result= 2;

        if(utenteDAO.doRetriveByKey(dati.getEmail(), IdentificativiUtente.EMAIL)!=null)
            result= 0;
        else if(utenteDAO.doRetriveByKey(dati.getCf(), IdentificativiUtente.CF)!=null)
            result= 1;

        return result;
    }

    @GetMapping("/checkOTP")
    public String checkOTP(HttpServletRequest request, @RequestParam("otpSend") String otpSend) {
        String response= "";

        HttpSession session= request.getSession(false);
        if(session!=null && session.getCreationTime()<600000) {
            boolean otpControl= BCrypt.checkpw(otpSend, (String)session.getAttribute("control"));
            if(otpControl)
                response= "OTP corretto";
            else
                response= "OTP errato";
        }
        else if(session!=null && session.getCreationTime()>=600000)
            response= "Sessione scaduta";
        else
            response= "Sessione non esistente";

        return response;
    }

    /*@PostMapping("/insertUser")
    public boolean insertUser(@RequestBody DatiRegistrazione dati) {

    }*/
}
