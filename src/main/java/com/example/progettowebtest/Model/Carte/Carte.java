package com.example.progettowebtest.Model.Carte;

import com.example.progettowebtest.Model.ContoCorrente.ContoCorrente;
import com.example.progettowebtest.Model.Stato;

import java.sql.Date;

public interface Carte {
    String getNumCarta() {return numCarta;}
    boolean isPagamentoOnline() {return pagamentoOnline;}
    Date getDataCreazione() {return dataCreazione;}
    Date getDataScadenza() {return dataScadenza;}
    String getCvv() {return cvv;}
    boolean isCartaFisica() {return cartaFisica;}
    double getCanoneMensile() {return canoneMensile;}
    String getPin() {return pin;}
    ContoCorrente getContoRiferimento() {return contoRiferimento;}
    Stato getStatoCarta() {return statoCarta;}
}
