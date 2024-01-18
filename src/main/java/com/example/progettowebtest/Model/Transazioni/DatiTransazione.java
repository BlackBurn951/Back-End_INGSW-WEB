package com.example.progettowebtest.Model.Transazioni;

import java.sql.Date;

public abstract class DatiTransazione {
    private Date dataTransazione;
    private double costoTransazione;
    private boolean esito;

    public DatiTransazione(String dataTransazione, double costoTransazione, boolean esito) {
        this.dataTransazione = Date.valueOf(dataTransazione);
        this.costoTransazione = costoTransazione;
        this.esito = esito;
    }

    public Date getDataTransazione() {return dataTransazione;}
    public double getCostoTransazione() {return costoTransazione;}
    public boolean getEsito() {return esito;}
}
