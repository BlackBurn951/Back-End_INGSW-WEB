package com.example.progettowebtest.ClassiRequest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Card {
    @JsonProperty("numeroCarta")
    String numeroCarta;

    @JsonProperty("pagamentoOnline")
    boolean pagamentoOnline;

    @JsonProperty("titolareCarta")
    String titolareCarta;

    @JsonProperty("dataScadenza")
    String dataScadenza;

    @JsonProperty("cvv")
    String cvv;

    @JsonProperty("canoneMensile")
    double canoneMensile;

    @JsonProperty("fido")
    double fido;

    @JsonProperty("statoCarta")
    String statoCarta;


    public Card(String numeroCarta, boolean pagamentoOnline, String titolareCarta, String dataScadenza, String cvv, double canoneMensile, double fido, String statoCarta) {
        this.numeroCarta = numeroCarta;
        this.pagamentoOnline = pagamentoOnline;
        this.titolareCarta = titolareCarta;
        this.dataScadenza = dataScadenza;
        this.cvv = cvv;
        this.canoneMensile = canoneMensile;
        this.fido = fido;
        this.statoCarta = statoCarta;
    }
}
