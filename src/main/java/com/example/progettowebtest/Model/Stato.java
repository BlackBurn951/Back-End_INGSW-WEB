package com.example.progettowebtest.Model;

import java.sql.Date;

public class Stato {
    private int idStato;
    private String valoreStato;
    private Date dataInizioStato;
    private Date dataFineStato;

    public Stato(int idStato, String valoreStato, String dataInizioStato, String dataFineStato) {
        this.idStato = idStato;
        this.valoreStato = valoreStato;
        this.dataInizioStato = Date.valueOf(dataInizioStato);
        this.dataFineStato = Date.valueOf(dataFineStato);
    }

    public int getIdStato() {return idStato;}
    public String getValoreStato() {return valoreStato;}
    public Date getDataInizioStato() {return dataInizioStato;}
    public Date getDataFineStato() {return dataFineStato;}
}
