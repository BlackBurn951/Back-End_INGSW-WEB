package com.example.progettowebtest.Model.Carte;

import com.example.progettowebtest.Model.ContoCorrente.ContoCorrente;
import com.example.progettowebtest.Model.Stato;

import java.sql.Date;

public class RelStatoCarta {
    private int id;
    private Date dataInizioStato;
    private Date dataFineStato;
    private Stato st;
    private Carte carta;

    public RelStatoCarta(int id, String dataInizioStato, String dataFineStato, Stato st, Carte carta) {
        this.id = id;
        this.dataInizioStato = Date.valueOf(dataInizioStato);
        this.dataFineStato = Date.valueOf(dataFineStato);
        this.st = st;
        this.carta = carta;
    }

    public int getId() {return id;}
    public Date getDataInizioStato() {return dataInizioStato;}
    public Date getDataFineStato() {return dataFineStato;}
    public Stato getStato() {return st;}
    public Carte getCarta() {return carta;}

    public void setDataFineStato(Date dataFineStato) {this.dataFineStato = dataFineStato;}
}
