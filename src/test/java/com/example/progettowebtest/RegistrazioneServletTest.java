package com.example.progettowebtest;

import com.example.progettowebtest.ClassiRequest.DatiControlloUtente;
import com.example.progettowebtest.ClassiRequest.DatiRegistrazione;
import com.example.progettowebtest.ClassiRequest.IdentificativiUtente;
import com.example.progettowebtest.DAO.MagnusDAO;
import com.example.progettowebtest.DAO.Utente_Documenti.UtenteDAOImpl;
import com.example.progettowebtest.Model.Utente_Documenti.Utente;
import com.example.progettowebtest.Servlet.RegistrazioneServlet;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {RegistrazioneServlet.class, MagnusDAO.class})
@WebMvcTest
public class RegistrazioneServletTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private MagnusDAO magnus;
    @Mock
    private UtenteDAOImpl utenteDAO;
    @Mock
    private Utente ut;

    private RegistrazioneServlet servlet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        servlet= new RegistrazioneServlet(magnus);
        mockMvc = MockMvcBuilders.standaloneSetup(servlet).build();
    }

    @Test
    public void testEmailCheck() throws Exception {

        //Impostazione comportamento del Mock
        when(utenteDAO.doRetriveByKey(eq("paperonezio53@gmail.com"),eq(IdentificativiUtente.EMAIL))).thenReturn(null);
        when(utenteDAO.doRetriveByKey(eq("asdfgh03d38d086b"),eq(IdentificativiUtente.CF))).thenReturn(null);
        when(magnus.getUtenteDAO()).thenReturn(utenteDAO);

        //Dati da passare alla chiamata Http
        DatiControlloUtente dati= new DatiControlloUtente();
        dati.setEmail("paperonezio53@gmail.com");
        dati.setCf("asdfgh03d38d086b");

        String jsonChiamata= new ObjectMapper().writeValueAsString(dati);

        //Chiamata Http
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/emailCheck").content(jsonChiamata)
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        //Risposta della chiamata Http
        String rispostaChiamata= result.getResponse().getContentAsString();
        int rispostaEndPoint= Integer.parseInt(rispostaChiamata);

        String headerRisposta= result.getResponse().getHeader("Session-ID");

        assertEquals(2, rispostaEndPoint);
        assertNotNull(headerRisposta);
    }

    @Test
    public void testEmailCheckCfEsistente() throws Exception {

        //Impostazione comportamento del Mock
        when(utenteDAO.doRetriveByKey(any(String.class),eq(IdentificativiUtente.EMAIL))).thenReturn(null);
        when(utenteDAO.doRetriveByKey(eq("asdfgh03d38d086b"),eq(IdentificativiUtente.CF))).thenReturn(ut);
        when(magnus.getUtenteDAO()).thenReturn(utenteDAO);


        //Dati da passare alla chiamata Http
        DatiControlloUtente dati= new DatiControlloUtente();
        dati.setEmail("paperonezio53@gmail.com");
        dati.setCf("asdfgh03d38d086b");

        String jsonChiamata= new ObjectMapper().writeValueAsString(dati);

        //Chiamata Http
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/emailCheck").content(jsonChiamata)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        //Risposta della chiamata Http
        String rispostaChiamata= result.getResponse().getContentAsString();
        int rispostaEndPoint= Integer.parseInt(rispostaChiamata);

        String headerRisposta= result.getResponse().getHeader("Session-ID");

        assertEquals(1, rispostaEndPoint);
        assertNull(headerRisposta);
    }

    @Test
    public void testEmailCheckEmailEsistente() throws Exception {

        //Impostazione comportamento del Mock
        when(utenteDAO.doRetriveByKey(eq("paperonezio53@gmail.com"),eq(IdentificativiUtente.EMAIL))).thenReturn(ut);
        when(utenteDAO.doRetriveByKey(any(String.class),eq(IdentificativiUtente.CF))).thenReturn(null);
        when(magnus.getUtenteDAO()).thenReturn(utenteDAO);


        //Dati da passare alla chiamata Http
        DatiControlloUtente dati= new DatiControlloUtente();
        dati.setEmail("paperonezio53@gmail.com");
        dati.setCf("asdfgh03d38d086b");

        String jsonChiamata= new ObjectMapper().writeValueAsString(dati);

        //Chiamata Http
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/emailCheck").content(jsonChiamata)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        //Risposta della chiamata Http
        String rispostaChiamata= result.getResponse().getContentAsString();
        int rispostaEndPoint= Integer.parseInt(rispostaChiamata);

        String headerRisposta= result.getResponse().getHeader("Session-ID");

        assertEquals(0, rispostaEndPoint);
        assertNull(headerRisposta);
    }
}