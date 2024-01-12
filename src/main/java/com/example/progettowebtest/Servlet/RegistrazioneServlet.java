package com.example.progettowebtest.Servlet;

import com.example.progettowebtest.ClassiRequest.DatiControlloUtente;
import com.example.progettowebtest.ClassiRequest.DatiRegistrazione;
import com.example.progettowebtest.DAO.Utente_Documenti.UtenteDAO;
import com.example.progettowebtest.DAO.Utente_Documenti.UtenteDAOImpl;
import com.example.progettowebtest.ClassiRequest.IdentificativiUtente;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class RegistrazioneServlet extends HttpServlet {
    private UtenteDAO utenteDAO= UtenteDAOImpl.getInstance();

    @PostMapping("/emailCheck")
    public boolean emailCheck(@RequestBody DatiControlloUtente dati) {
        boolean result= false;
        if(utenteDAO.doRetriveByKey(dati.getEmail(), IdentificativiUtente.EMAIL)== null &&
                utenteDAO.doRetriveByKey(dati.getCf(), IdentificativiUtente.CF)== null)
            result= true;

        return result;
    }

    @PostMapping("/checkOTP")
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
