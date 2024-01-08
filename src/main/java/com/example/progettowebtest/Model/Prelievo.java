package com.example.progettowebtest.Model;

public class Prelievo {

    private int idPrelievo;
    private double importo;
    private Mezzo mezzo;
    private CartaCredito cartaCredito;
    private CartaDebito cartaDebito;

    public Prelievo(int idPrelievo, double importo, Mezzo mezzo, CartaCredito cartaCredito, CartaDebito cartaDebito) {
        this.idPrelievo = idPrelievo;
        this.importo = importo;
        this.mezzo = mezzo;
        this.cartaCredito = cartaCredito;
        this.cartaDebito = cartaDebito;
    }

    public int getIdPrelievo() {
        return idPrelievo;
    }

    public double getImporto() {
        return importo;
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
