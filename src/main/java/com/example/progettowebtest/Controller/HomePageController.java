package com.example.progettowebtest.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomePageController{

    @GetMapping({"/homepage_logged"})
    public String caricaDati(HttpServletRequest request, @RequestParam("IDSession") String idSession, Model model) {
        HttpSession session= (HttpSession) request.getServletContext().getAttribute(idSession);
        model.addAttribute("utente", session.getAttribute("Utente"));
        model.addAttribute("conto", session.getAttribute("Conto"));

        return "/homepage_logged";
    }

    @GetMapping({"/personal_data"})
    public String caricaDatiPersonali(HttpServletRequest request, @RequestParam("IDSession") String idSession, Model model) {
        HttpSession session= (HttpSession) request.getServletContext().getAttribute(idSession);
        model.addAttribute("utente", session.getAttribute("Utente"));
        model.addAttribute("conto", session.getAttribute("Conto"));


        return "/personal_data";
    }


}
