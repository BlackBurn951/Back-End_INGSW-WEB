package com.example.progettowebtest.Servlet;

import com.example.progettowebtest.ClassiRequest.DatiControlloUtente;
import com.example.progettowebtest.DAO.Utente_Documenti.UtenteDAO;
import com.example.progettowebtest.DAO.Utente_Documenti.UtenteDAOImpl;
import com.example.progettowebtest.ClassiRequest.IdentificativiUtente;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpSession;


@RestController
public class RegistrazioneServlet extends HttpServlet {
    private UtenteDAO utenteDAO= UtenteDAOImpl.getInstance();

    @PostMapping("/emailCheck")
    public int emailCheck(@RequestBody DatiControlloUtente dati) {
        int result= 2;

        if(utenteDAO.doRetriveByKey(dati.getEmail(), IdentificativiUtente.EMAIL)!=null)
            result= 0;
        else if(utenteDAO.doRetriveByKey(dati.getCf(), IdentificativiUtente.CF)!=null)
            result= 1;

        System.out.println("Valore result: "+ result);
        return result;
    }

    @GetMapping("/checkOTP")
    public String checkOTP(HttpSession session, @RequestParam("otpSend") String otpSend) {
        String response= "";
        if(session != null && System.currentTimeMillis() - session.getCreationTime() < 600000) {
            System.out.println("Otp criptato: "+ (String)session.getAttribute("control"));
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
