package com.example.progettowebtest.Servlet;

import com.example.progettowebtest.ClassiRequest.CambioPassword;
import com.example.progettowebtest.ClassiRequest.IdentificativiUtente;
import com.example.progettowebtest.DAO.MagnusDAO;
import com.example.progettowebtest.Model.Utente_Documenti.Utente;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:4200", exposedHeaders = "Session-ID")
public class LoginServlet {

    @GetMapping("/login")
    public String doLogin(HttpServletRequest request, HttpServletResponse response, @RequestParam("username") String username, @RequestParam("password") String password) {
        Utente ut = MagnusDAO.getInstance().getUtenteDAO().doRetriveByKey(username, IdentificativiUtente.EMAIL);

        if (ut != null && BCrypt.checkpw(password, ut.getPassword())) {
            HttpSession session= request.getSession(true);
            session.setAttribute("Utente", ut);
            session.setAttribute("Conto", MagnusDAO.getInstance().getContoCorrenteDAO().doRetriveByAttribute(ut.getCodiceFiscale()));

            request.getServletContext().setAttribute(session.getId(), session);

            response.setHeader("Session-ID", session.getId());
            return session.getId();
        }
        return "";
    }

    @GetMapping("/checkUser")
    public boolean checkUser(@RequestParam("username") String username) {
        boolean result= false;

        Utente ut= MagnusDAO.getInstance().getUtenteDAO().doRetriveByKey(username, IdentificativiUtente.EMAIL);
        if(ut!=null)
            result= true;

        return result;
    }

    @PostMapping("/recuperaPass")
    public void recuperaPass(@RequestBody CambioPassword cambio) {
        Utente ut= MagnusDAO.getInstance().getUtenteDAO().doRetriveByKey(cambio.getEmail(), IdentificativiUtente.EMAIL);
        ut.setPassword(cambio.getPassword());
        MagnusDAO.getInstance().getUtenteDAO().saveOrUpdate(ut);
    }
}
