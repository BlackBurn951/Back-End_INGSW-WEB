package com.example.progettowebtest.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomePageController {

    @GetMapping({"/homepage_logged"})
    public String caricaDati(Model model) {
        model.addAttribute("name", "ajeje");
        System.out.println("Attributo " + model.getAttribute("name"));

        return "/homepage_logged";
    }
}
