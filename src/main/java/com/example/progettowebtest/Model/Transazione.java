package com.example.progettowebtest.Model;

import java.sql.Date;

public interface Transazione {
    Date getDataTransazione();
    double getImporto();
    String getCausale();
    double getCostoTransazione();
    String getEsito();
    String getNomeBeneficiario();
    public String getCognomeBeneficiario();
    int getId();
    String getIbanDestinatario();
    String getNumCcDest();
    TipologiaBollettino getTipoBol();
    String getValutaPagamento();
    String getPaeseDestinatario();
    Mezzo getMezzo();
    Carta getCartaEsecuzione();
}
