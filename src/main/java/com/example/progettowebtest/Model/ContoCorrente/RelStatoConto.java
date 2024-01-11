package com.example.progettowebtest.Model.ContoCorrente;

import com.example.progettowebtest.Model.ContoCorrente.ContoCorrente;
import com.example.progettowebtest.Model.Stato;

import java.sql.Date;

public class RelStatoConto {
    private int id;
    private Date dataInizioStato;
    private Date dataFineStato;
    private Stato st;
    private ContoCorrente conto;

    public RelStatoConto(int id, String dataInizioStato, String dataFineStato, Stato st, ContoCorrente conto) {
        this.id = id;
        this.dataInizioStato = Date.valueOf(dataInizioStato);
        this.dataFineStato = Date.valueOf(dataFineStato);
        this.st = st;
        this.conto = conto;
    }

    public int getId() {return id;}
    public Date getDataInizioStato() {return dataInizioStato;}
    public Date getDataFineStato() {return dataFineStato;}
    public Stato getStato() {return st;}
    public ContoCorrente getConto() {return conto;}
}
