package com.example.progettowebtest.Model.ContoCorrente;

import com.example.progettowebtest.Model.Stato;

import java.sql.Date;

public class RelStatoConto {
    private int id= -1;
    private Date dataInizioStato;
    private Date dataFineStato;
    private Stato st;
    private ContoCorrente conto;

    public RelStatoConto(String dataInizioStato, Stato st, ContoCorrente conto) {
        this.dataInizioStato = Date.valueOf(dataInizioStato);
        this.st = st;
        this.conto = conto;
    }

    public int getId() {return id;}
    public Date getDataInizioStato() {return dataInizioStato;}
    public Date getDataFineStato() {return dataFineStato;}
    public Stato getStato() {return st;}
    public ContoCorrente getConto() {return conto;}

    public void setId(int id) {this.id = id;}
    public void setDataFineStato(String data) {dataFineStato= Date.valueOf(data);}
}
