package com.example.progettowebtest.Servlet;

import com.example.progettowebtest.ClassiRequest.DatiControlloUtente;
import com.example.progettowebtest.ClassiRequest.DatiRegistrazione;
import com.example.progettowebtest.DAO.Utente_Documenti.UtenteDAO;
import com.example.progettowebtest.DAO.Utente_Documenti.UtenteDAOImpl;
import com.example.progettowebtest.ClassiRequest.IdentificativiUtente;
import jakarta.servlet.http.*;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;


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
        String response= "test";


        Cookie[] biscotti= request.getCookies();
        if(biscotti!=null) {
            System.out.println("Array cookie non vuoto");
            for(Cookie a: biscotti){
                System.out.println("Valore biscotto: "+a.getAttribute("Pino"));
            }
        }
        /*HttpSession session= request.getSession(false);
        if(session!=null && session.getCreationTime()<600000) {
            boolean otpControl= BCrypt.checkpw(otpSend, (String)session.getAttribute("control"));
            if(otpControl)
                response= "OTP corretto";
            else
                response= "OTP errato";
        }
        else if(session!=null && session.getCreationTime()>=600000)
            response= "Sessione scaduta";
        else {
            System.out.println("Server: sessione non trovata!!!");
            response= "Sessione non esistente!!!";
        }*/

        return response;
    }

    /*@PostMapping("/insertUser")
    public boolean insertUser(@RequestBody DatiRegistrazione dati) {

    }*/
}
