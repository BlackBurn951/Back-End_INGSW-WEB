package com.example.progettowebtest.Model;

public class Deposito extends Transazione{
    private int idDeposito;
    private double importo;
    private String casuale;
    private Mezzo mezzo;
    private Carta cartaEsecuzione;

    public Deposito(String dataTransazione, double costoTransazione, String esito, int idDeposito,
                    double importo, String casuale, Mezzo mezzo, Carta cartaEsecuzione) {
        super(dataTransazione, costoTransazione, esito);
        this.idDeposito = idDeposito;
        this.importo = importo;
        this.casuale = casuale;
        this.mezzo = mezzo;
        this.cartaEsecuzione = cartaEsecuzione;
    }

    public int getIdDeposito() {return idDeposito;}
    public double getImporto() {return importo;}
    public String getCasuale() {return casuale;}
    public Mezzo getMezzo() {return mezzo;}
    public Carta getCartaEsecuzione() {return cartaEsecuzione;}
}
