package com.example.progettowebtest.Model;

import java.sql.Date;

public abstract class Carta {
    private String numCarta;
    private boolean pagamentoOnline;
    private Date dataCreazione;
    private Date dataScadenza;
    private String cvv;
    private boolean cartaFisica;
    private double canoneMensile;
    private String pin;
    private Stato statoCarta;
    private ContoCorrente contoRiferimento;

    public Carta(String numCarta, boolean pagamentoOnline, String dataCreazione, String dataScadenza, String cvv, boolean cartaFisica,
                 double canoneMensile, String pin, Stato statoCarta, ContoCorrente contoRiferimento) {
        this.numCarta = numCarta;
        this.pagamentoOnline = pagamentoOnline;
        this.dataCreazione = Date.valueOf(dataCreazione);
        this.dataScadenza = Date.valueOf(dataScadenza);
        this.cvv = cvv;
        this.cartaFisica = cartaFisica;
        this.canoneMensile = canoneMensile;
        this.pin = pin;
        this.statoCarta = statoCarta;
        this.contoRiferimento = contoRiferimento;
    }

    public String getNumCarta() {return numCarta;}
    public boolean isPagamentoOnline() {return pagamentoOnline;}
    public Date getDataCreazione() {return dataCreazione;}
    public Date getDataScadenza() {return dataScadenza;}
    public String getCvv() {return cvv;}
    public boolean isCartaFisica() {return cartaFisica;}
    public double getCanoneMensile() {return canoneMensile;}
    public String getPin() {return pin;}
    public ContoCorrente getContoRiferimento() {return contoRiferimento;}
    public Stato getStatoCarta() {return statoCarta;}
}
