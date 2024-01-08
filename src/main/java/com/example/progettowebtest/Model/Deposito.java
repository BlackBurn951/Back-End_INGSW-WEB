package com.example.progettowebtest.Model;

public class Deposito {

    private int idDeposito;

    private double importo;
    private String casuale;
    private Mezzo mezzo;

    private CartaCredito cartaCredito;

    private CartaDebito cartaDebito;

    public Deposito(int idDeposito, double importo, String casuale, Mezzo mezzo, CartaCredito cartaCredito, CartaDebito cartaDebito) {
        this.idDeposito = idDeposito;
        this.importo = importo;
        this.casuale = casuale;
        this.mezzo = mezzo;
        this.cartaCredito = cartaCredito;
        this.cartaDebito = cartaDebito;
    }

    public int getIdDeposito() {
        return idDeposito;
    }

    public double getImporto() {
        return importo;
    }

    public String getCasuale() {
        return casuale;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public CartaCredito getCartaCredito() {
        return cartaCredito;
    }

    public CartaDebito getCartaDebito() {
        return cartaDebito;
    }
}
