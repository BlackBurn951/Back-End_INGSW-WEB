package com.example.progettowebtest;

import com.example.progettowebtest.ClassiRequest.CambioPassword;
import com.example.progettowebtest.ClassiRequest.IdentificativiUtente;
import com.example.progettowebtest.DAO.ContoCorrente_StatoConto.ContoCorrenteDAOImpl;
import com.example.progettowebtest.DAO.MagnusDAO;
import com.example.progettowebtest.DAO.Utente_Documenti.UtenteDAO;
import com.example.progettowebtest.Model.Utente_Documenti.Patente;
import com.example.progettowebtest.Model.Utente_Documenti.Utente;
import com.example.progettowebtest.Servlet.LoginServlet;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static junit.framework.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {LoginServlet.class, MagnusDAO.class})
@WebMvcTest
class LoginServletTest {
    @Mock
    private UtenteDAO utenteDAOMock;
    @Mock
    private ContoCorrenteDAOImpl contoCorrenteDAOMock;
    @Mock
    private MagnusDAO magnusDAOMock;
    @Autowired
    private MockMvc mockMvc;
    private LoginServlet loginServlet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        loginServlet = new LoginServlet(magnusDAOMock);
        mockMvc = MockMvcBuilders.standaloneSetup(loginServlet).build();
    }

    @Test
    void doLogin() throws Exception {
        Utente mockUtente = Mockito.mock(Utente.class);
        when(utenteDAOMock.doRetriveByKey(anyString(), any(IdentificativiUtente.class))).thenReturn(mockUtente);
        String hashedPassword = BCrypt.hashpw("testPassword", BCrypt.gensalt());
        when(mockUtente.getPassword()).thenReturn(hashedPassword);
        when(magnusDAOMock.getUtenteDAO()).thenReturn(utenteDAOMock);
        when(magnusDAOMock.getContoCorrenteDAO()).thenReturn(contoCorrenteDAOMock);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/login?username=testUsername&password=testPassword"))
                .andExpect(status().isOk()).andReturn();

        String sessionId= result.getResponse().getContentAsString();
        String headerRisposta= result.getResponse().getHeader("Session-ID");

        assertNotEquals("",sessionId);
        assertNotNull(headerRisposta);
    }

    @Test
    void checkUser() throws Exception {
        Utente mockUtente = Mockito.mock(Utente.class);
        when(utenteDAOMock.doRetriveByKey(eq("testUtente"), any(IdentificativiUtente.class))).thenReturn(mockUtente);
        when(magnusDAOMock.getUtenteDAO()).thenReturn(utenteDAOMock);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/checkUser?username=testUtente"))
                .andExpect(status().isOk()).andReturn();

        String risposta= result.getResponse().getContentAsString();

        assertTrue(Boolean.parseBoolean(risposta));
    }

    @Test
    void checkUserFailed() throws Exception {
        when(utenteDAOMock.doRetriveByKey(eq("testUtente"), any(IdentificativiUtente.class))).thenReturn(null);
        when(magnusDAOMock.getUtenteDAO()).thenReturn(utenteDAOMock);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/checkUser?username=testUtente"))
                .andExpect(status().isOk()).andReturn();

        String risposta= result.getResponse().getContentAsString();

        assertFalse(Boolean.parseBoolean(risposta));
    }
}