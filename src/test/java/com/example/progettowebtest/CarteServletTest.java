package com.example.progettowebtest;

import com.example.progettowebtest.DAO.MagnusDAO;
import com.example.progettowebtest.Model.Utente_Documenti.Utente;
import com.example.progettowebtest.Servlet.CarteServlet;
import com.example.progettowebtest.Servlet.RegistrazioneServlet;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {RegistrazioneServlet.class, MagnusDAO.class})
@WebMvcTest
public class CarteServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private ServletContext servletContext;
    @Mock
    private Utente ut;

    private CarteServlet servlet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        servlet= new CarteServlet();
    }
    @Test
    public void creaCartaTest() throws Exception{

        //Impostazione comportamento dei Mock
        when(ut.getNome()).thenReturn("Nome prova");
        when(ut.getCognome()).thenReturn("Cognome prova");
        when(session.getAttribute(eq("Utente"))).thenReturn(ut);
        when(servletContext.getAttribute(eq("IdSessione"))).thenReturn(session);
        when(request.getServletContext()).thenReturn(servletContext);

        List<String> daticarta= servlet.creaCarta(request, "IdSessione", true);

        assertNotNull(daticarta);
        assertEquals("Nome prova Cognome prova", daticarta.get(2));
    }
}
