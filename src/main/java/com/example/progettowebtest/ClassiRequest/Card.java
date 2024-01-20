package com.example.progettowebtest.ClassiRequest;

public class Card {

    String numeroCarta;
    boolean pagamentoOnline;
    String titolareCarta;
    String dataScadenza;
    String cvv;
    double canoneMensile;
    double fido;
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
