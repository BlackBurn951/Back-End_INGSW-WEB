package com.example.progettowebtest.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/redirect")
public class RedirectController {

    @GetMapping
    public String redirectToAngular() {
        // Effettua la redirezione alla tua pagina Angular
        return "redirect:http://indirizzo-anguar:porta";
    }
}
