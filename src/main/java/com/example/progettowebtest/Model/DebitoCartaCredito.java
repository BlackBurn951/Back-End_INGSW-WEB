package com.example.progettowebtest.Model;

import java.sql.Date;

public class DebitoCartaCredito {
    private int idDebito;
    private double interesse;
    private double ammontare;
    private double debitoSaldato;
    private Date dataInizioDebito;
    private Date dataInizioInteresse;
    private Date dataEstinzioneDebito;

    public DebitoCartaCredito(int idDebito, double interesse, double ammontare, double debitoSaldato, String dataInizioDebito,
                              String dataInizioInteresse, String dataEstinzioneDebito) {
        this.idDebito = idDebito;
        this.interesse = interesse;
        this.ammontare = ammontare;
        this.debitoSaldato = debitoSaldato;
        this.dataInizioDebito = Date.valueOf(dataInizioDebito);
        this.dataInizioInteresse = Date.valueOf(dataInizioInteresse);
        this.dataEstinzioneDebito = Date.valueOf(dataEstinzioneDebito);
    }

    public int getIdDebito() {return idDebito;}
    public double getInteresse() {return interesse;}
    public double getAmmontare() {return ammontare;}
    public double getDebitoSaldato() {return debitoSaldato;}
    public Date getDataInizioDebito() {return dataInizioDebito;}
    public Date getDataInizioInteresse() {return dataInizioInteresse;}
    public Date getDataEstinzioneDebito() {return dataEstinzioneDebito;}
}
