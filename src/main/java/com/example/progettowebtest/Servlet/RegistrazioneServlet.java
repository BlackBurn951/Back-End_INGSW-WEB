package com.example.progettowebtest.Servlet;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class RegistrazioneServlet extends HttpServlet {

    @PostMapping("/checkUser")
    public void checkUser(HttpServletRequest request, HttpServletResponse response) {

    }
}
