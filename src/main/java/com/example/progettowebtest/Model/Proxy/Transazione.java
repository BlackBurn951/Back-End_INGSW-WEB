package com.example.progettowebtest.Model.Proxy;

import com.example.progettowebtest.Model.Carte.Carta;
import com.example.progettowebtest.Model.Transazioni.Mezzo;
import com.example.progettowebtest.Model.Transazioni.TipologiaBollettino;

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
