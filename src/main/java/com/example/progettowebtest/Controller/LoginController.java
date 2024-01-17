package com.example.progettowebtest.Controller;
/*
import com.example.progettowebtest.ClassiRequest.IdentificativiUtente;
import com.example.progettowebtest.DAO.MagnusDAO;
import com.example.progettowebtest.Model.Utente_Documenti.Utente;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Controller
@CrossOrigin(origins = "http://localhost:4200", exposedHeaders = "Session-ID")
public class LoginController {

    @GetMapping("/login")
    public boolean doLogin(HttpServletRequest request, HttpServletResponse response, @RequestParam("username") String username, @RequestParam("password") String password, Model model) throws ServletException, IOException {
        Utente ut = MagnusDAO.getInstance().getUtenteDAO().doRetriveByKey(username, IdentificativiUtente.EMAIL);

        if (ut != null && BCrypt.checkpw(password, ut.getPassword())) {

            return true;

        }
        return false;

    }
}*/
