package com.example.progettowebtest;

import com.example.progettowebtest.ClassiRequest.DatiRegistrazione;
import com.example.progettowebtest.ClassiRequest.IdentificativiUtente;
import com.example.progettowebtest.Connection.DbConn;
import com.example.progettowebtest.DAO.ContoCorrente_StatoConto.ContoCorrenteDAO;
import com.example.progettowebtest.DAO.Indirizzo.DatiComuneDAO;
import com.example.progettowebtest.DAO.Indirizzo.TipoViaDAO;
import com.example.progettowebtest.DAO.MagnusDAO;
import com.example.progettowebtest.DAO.Utente_Documenti.PassaportoDAO;
import com.example.progettowebtest.DAO.Utente_Documenti.PatenteDAO;
import com.example.progettowebtest.DAO.Utente_Documenti.UtenteDAO;
import com.example.progettowebtest.Model.Indirizzo.TipoVia;
import com.example.progettowebtest.Model.Utente_Documenti.Patente;
import com.example.progettowebtest.Model.Utente_Documenti.Utente;
import com.example.progettowebtest.Servlet.RegistrazioneServlet;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


import com.example.progettowebtest.ClassiRequest.DatiControlloUtente;
import com.example.progettowebtest.ClassiRequest.DatiRegistrazione;
import com.example.progettowebtest.DAO.MagnusDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.example.progettowebtest.Servlet.RegistrazioneServlet;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;


public class RegistrazioneServletTest {

    @Mock
    private HttpServletRequest mockRequest;

    @Mock
    private HttpServletResponse mockResponse;

    @Mock
    private DataSource mockDataSource;

    @Mock
    private HttpSession mockSession;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        MagnusDAO mockMagnusDAO = mock(MagnusDAO.class);

        TipoViaDAO mockTipoViaDAO = mock(TipoViaDAO.class);
        when(mockTipoViaDAO.doRetriveByAttribute(anyString())).thenReturn(null);

        DatiComuneDAO mockDatiComuneDAO = mock(DatiComuneDAO.class);
        when(mockDatiComuneDAO.doRetriveByAttribute(anyString(), any())).thenReturn(null);

        PassaportoDAO mockPassaportoDAO = mock(PassaportoDAO.class);

        ContoCorrenteDAO mockContoCorrenteDAO = mock(ContoCorrenteDAO.class);

        UtenteDAO mockUtenteDAO = mock(UtenteDAO.class);

        when(mockMagnusDAO.getTipoViaDAO()).thenReturn(mockTipoViaDAO);
        when(mockMagnusDAO.getDatiComuneDAO()).thenReturn(mockDatiComuneDAO);
        when(mockMagnusDAO.getPassaportoDAO()).thenReturn(mockPassaportoDAO);
        when(mockMagnusDAO.getContoCorrenteDAO()).thenReturn(mockContoCorrenteDAO);
        when(mockMagnusDAO.getUtenteDAO()).thenReturn(mockUtenteDAO);

        DbConn.setDataSource(mockDataSource);
        when(DbConn.getDataSource()).thenReturn(mockDataSource);
    }

    @Test
    public void testInsertUser() {

        DatiRegistrazione datiRegistrazione = new DatiRegistrazione();
        datiRegistrazione.setNome("Mario");
        datiRegistrazione.setCognome("Rossi");
        datiRegistrazione.setCellulare("3497276241");
        datiRegistrazione.setEmail("secondaryasdasd@gmail.com");
        datiRegistrazione.setCf("BNNLCU01MN24D086X");
        datiRegistrazione.setDataNascita("24-08-2001");
        datiRegistrazione.setSesso("maschio");
        datiRegistrazione.setComuneNas("Cusenza");
        datiRegistrazione.setProvNas("CS");
        datiRegistrazione.setCittadinanza("Italiana");
        datiRegistrazione.setTipoStradaRes("via");
        datiRegistrazione.setNomeStradaRes("Giuseppe");
        datiRegistrazione.setNumCivicoRes("45");
        datiRegistrazione.setCittaRes("Rende");
        datiRegistrazione.setCapRes("87058");
        datiRegistrazione.setProvinciaRes("CS");
        datiRegistrazione.setRegioneRes("Calabria");
        datiRegistrazione.setTipoDoc("Passaporto");
        datiRegistrazione.setIdDoc("1");
        datiRegistrazione.setComuneAutorita("Comune");
        datiRegistrazione.setNazionalita("Italiana");
        datiRegistrazione.setNomeDocumento("Mario");
        datiRegistrazione.setCognomeDocumento("Rossi");
        datiRegistrazione.setDataNascitaDocumento("24-08-2001");
        datiRegistrazione.setDataEmissione("23-03-2000");
        datiRegistrazione.setDataScadenza("25-05-2025");
        datiRegistrazione.setComuneNascitaDoc("Cusenza");
        datiRegistrazione.setProvNascitaDoc("CS");
        datiRegistrazione.setSessoDoc("Maschio");
        datiRegistrazione.setOccupazione("Studente");
        datiRegistrazione.setReddito(12345);
        datiRegistrazione.setPassword("CiaoCiao5_");
        // Mock MagnusDAO
        MagnusDAO mockMagnusDAO = mock(MagnusDAO.class);



        // Create instance of servlet
        RegistrazioneServlet servlet = new RegistrazioneServlet();

        // Test
        boolean result = servlet.insertUser(mockRequest, mockResponse, "sessionId123", datiRegistrazione);

        // Verify
        assertEquals(true, result); // Expected result based on mock data
    }

    // You can add more tests for insertUser method with different scenarios
}
