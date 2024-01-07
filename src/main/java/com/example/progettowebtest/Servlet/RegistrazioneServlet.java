package com.example.progettowebtest.Servlet;

import com.example.progettowebtest.ClassiRequest.DatiControlloUtente;
import com.example.progettowebtest.ClassiRequest.DatiRegistrazione;
import com.example.progettowebtest.DAO.UtenteDAO;
import com.example.progettowebtest.DAO.UtenteDAOImpl;
import com.example.progettowebtest.Model.IdentificativiUtente;
import com.example.progettowebtest.Model.Utente;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class RegistrazioneServlet extends HttpServlet {
    private UtenteDAO utenteDAO= UtenteDAOImpl.getInstance();

    @PostMapping("/emailCheck")
    public void emailCheck(HttpServletRequest request, HttpServletResponse response, @RequestBody DatiControlloUtente dati) {
        Utente ut= utenteDAO.doRetriveByKey(dati.getEmail(), IdentificativiUtente.EMAIL);
        String addres;
        if(ut==null) {
            response.setStatus(200);
            addres= "src/app/registration2/registration2.component.html";
        }
        else {
            response.setStatus(302);
            addres= "src/app/registration1/userAlreadyEx.component.html";
        }
        RequestDispatcher dispatcher= getServletContext().getRequestDispatcher(addres);
        try {
            dispatcher.include(request, response);
        }catch (IOException | ServletException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/checkOTP")
    public void checkOTP(HttpServletRequest request, HttpServletResponse response) {

    }
}
