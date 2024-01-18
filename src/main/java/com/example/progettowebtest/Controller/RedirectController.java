package com.example.progettowebtest.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/redirect")
public class RedirectController {

    @GetMapping("/vaiPaginaProfilo")
    public String redirectToProfile(@RequestParam(name = "IDSession") String sessionId) {

        return "redirect: http://localhost:8080/personal_data?IDSession=" + sessionId;
    }

    @GetMapping("/vaiPaginaCarte")
    public String redirectToCard(@RequestParam(name = "IDSession") String sessionId) {

        return "redirect:http://localhost:4200/card-page?IDSession=" + sessionId;
    }

    @GetMapping("/vaiPaginaPagamenti")
    public String redirectToPay(@RequestParam(name = "IDSession") String sessionId) {

        return "redirect:http://localhost:4200/transaction?IDSession=" + sessionId;
    }


    @GetMapping("/tornaHomepage")
    public String redirectToHomepage() {

        return "redirect:http://localhost:4200";
    }

    @GetMapping("/vaiLogged")
    public String redirectToHomepageLogged(@RequestParam(name = "IDSession") String sessionId) {

        return "redirect:http://localhost:8080/homepage_logged?IDSession=" + sessionId;
    }



}
