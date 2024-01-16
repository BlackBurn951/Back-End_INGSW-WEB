package com.example.progettowebtest.Servlet;

import com.example.progettowebtest.ClassiRequest.IdentificativiUtente;
import com.example.progettowebtest.DAO.MagnusDAO;
import com.example.progettowebtest.Model.Utente_Documenti.Utente;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200", exposedHeaders = "Session-ID")
public class LoginServlet {
    @GetMapping("/login")
    public boolean doLogin(@RequestParam("username") String username, @RequestParam("password") String password) {
        //AGGIUNGERE SESSIONE QUI E ALLA REGISTRAZIONE
        boolean result= false;

        Utente ut= MagnusDAO.getInstance().getUtenteDAO().doRetriveByKey(username, IdentificativiUtente.EMAIL);
        if(ut!=null && BCrypt.checkpw(password, ut.getPassword()))
            result= true;

        return result;
    }
}
