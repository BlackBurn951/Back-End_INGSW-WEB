package com.example.progettowebtest.Model.Proxy;

import com.example.progettowebtest.Model.Carte.Carta;
import com.example.progettowebtest.Model.Carte.Carte;
import com.example.progettowebtest.Model.Transazioni.Mezzo;
import com.example.progettowebtest.Model.Transazioni.TipologiaBollettino;

import java.sql.Date;

public interface Transazione {
    Date getDataTransazione();
    double getImporto();
    String getCausale();
    double getCostoTransazione();
    boolean getEsito();
    String getNomeBeneficiario();
    String getCognomeBeneficiario();
    int getId();
    String getIbanDestinatario();
    String getNumCcDest();
    TipologiaBollettino getTipoBol();
    String getValutaPagamento();
    String getPaeseDestinatario();
    Mezzo getMezzo();
    Carte getCartaEsecuzione();
    void setId(int id);
    String getTipoTrans();
}
