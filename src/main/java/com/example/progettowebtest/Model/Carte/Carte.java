package com.example.progettowebtest.Model.Carte;

import com.example.progettowebtest.Model.ContoCorrente.ContoCorrente;
import com.example.progettowebtest.Model.Stato;

import java.sql.Date;

public interface Carte {
    String getNumCarta();
    boolean isPagamentoOnline();
    Date getDataCreazione(); //NON MANDARE
    Date getDataScadenza();
    String getCvv();
    boolean isCartaFisica(); //NON MANDARE
    double getCanoneMensile();
    String getPin(); //NON MANDARE
    Double getFido();
    ContoCorrente getContoRiferimento(); //NON MANDARE
    Stato getStatoCarta();
}
