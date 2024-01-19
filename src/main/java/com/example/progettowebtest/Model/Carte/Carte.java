package com.example.progettowebtest.Model.Carte;

import com.example.progettowebtest.Model.ContoCorrente.ContoCorrente;
import com.example.progettowebtest.Model.Stato;

import java.sql.Date;

public interface Carte {
    String getNumCarta();
    boolean isPagamentoOnline();
    Date getDataCreazione();
    Date getDataScadenza();
    String getCvv();
    boolean isCartaFisica();
    double getCanoneMensile();
    String getPin();
    ContoCorrente getContoRiferimento();
    Stato getStatoCarta();
}
