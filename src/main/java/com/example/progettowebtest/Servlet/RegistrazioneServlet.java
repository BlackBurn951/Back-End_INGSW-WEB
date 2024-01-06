package com.example.progettowebtest.Servlet;

import com.example.progettowebtest.ClassiRequest.DatiAnagrafici;
import com.example.progettowebtest.ClassiRequest.DatiRegistrazione;
import com.example.progettowebtest.DAO.UtenteDAOImpl;
import com.example.progettowebtest.Model.Utente;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
public class RegistrazioneServlet extends HttpServlet {
    @PostMapping("/emailCheck")
    public void emailCheck(HttpServletRequest request, HttpServletResponse response, @RequestBody DatiAnagrafici dati) {
        Utente ut= UtenteDAOImpl.getInstance().doRetriveByKey(dati.getCf());
        String addres;
        if(ut==null) {
            response.setStatus(200);
            addres= "src/app/registration2/registration2.component.html";
            HttpSession session= request.getSession(true);
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
    public void checkOTP(HttpServletRequest request, HttpServletResponse response, @RequestBody DatiRegistrazione dati) {

    }
}
