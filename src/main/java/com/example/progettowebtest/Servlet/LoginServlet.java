package com.example.progettowebtest.Servlet;

import com.example.progettowebtest.ClassiRequest.CambioPassword;
import com.example.progettowebtest.ClassiRequest.IdentificativiUtente;
import com.example.progettowebtest.DAO.MagnusDAO;
import com.example.progettowebtest.Model.Utente_Documenti.Utente;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200", exposedHeaders = "Session-ID")
public class LoginServlet {

    @GetMapping("/login")
    public boolean doLogin(@RequestParam("username") String username, @RequestParam("password") String password) {
        boolean result= false;

        Utente ut= MagnusDAO.getInstance().getUtenteDAO().doRetriveByKey(username, IdentificativiUtente.EMAIL);
        if(ut!=null && BCrypt.checkpw(password, ut.getPassword()))
            result= true;

        return result;
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
        System.out.println("password nuova arrivata: "+cambio.getPassword());
        Utente ut= MagnusDAO.getInstance().getUtenteDAO().doRetriveByKey(cambio.getEmail(), IdentificativiUtente.EMAIL);
        System.out.println("Vecchia password criptata: "+ut.getPassword());
        ut.setPassword(cambio.getPassword());
        MagnusDAO.getInstance().getUtenteDAO().saveOrUpdate(ut);
        System.out.println("Nuova password criptata: "+ut.getPassword());
    }
}
