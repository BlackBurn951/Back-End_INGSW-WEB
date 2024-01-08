package com.example.progettowebtest.Model;

import java.sql.Date;

public abstract class Transazione {
    private Date dataTransazione;
    private double costoTransazione;
    private String esito;

    public Transazione(String dataTransazione, double costoTransazione, String esito) {
        this.dataTransazione = Date.valueOf(dataTransazione);
        this.costoTransazione = costoTransazione;
        this.esito = esito;
    }

    public Date getDataTransazione() {return dataTransazione;}
    public double getCostoTransazione() {return costoTransazione;}
    public String getEsito() {return esito;}
}
