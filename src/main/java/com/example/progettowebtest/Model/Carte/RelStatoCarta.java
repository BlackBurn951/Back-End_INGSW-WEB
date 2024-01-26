package com.example.progettowebtest.Model.Carte;

import com.example.progettowebtest.Model.Stato;

import java.sql.Date;

public class RelStatoCarta {
    private int id= -1;
    private Date dataInizioStato;
    private Date dataFineStato;
    private Stato st;
    private Carte carta;

    public RelStatoCarta(String dataInizioStato, Stato st, Carte carta) {
        this.dataInizioStato = Date.valueOf(dataInizioStato);
        this.st = st;
        this.carta = carta;
    }

    public int getId() {return id;}
    public Date getDataInizioStato() {return dataInizioStato;}
    public Date getDataFineStato() {return dataFineStato;}
    public Stato getStato() {return st;}
    public Carte getCarta() {return carta;}

    public void setId(int id) {this.id= id;}
    public void setDataFineStato(String dataFineStato) {this.dataFineStato = Date.valueOf(dataFineStato);}
}
