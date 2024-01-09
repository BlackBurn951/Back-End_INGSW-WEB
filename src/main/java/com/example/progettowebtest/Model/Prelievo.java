package com.example.progettowebtest.Model;

public class Prelievo extends DatiTransazione {
    private int idPrelievo;
    private double importo;
    private String casuale;
    private Mezzo mezzo;
    private Carta cartaEsecuzione;

    public Prelievo(String dataTransazione, double costoTransazione, String esito, int idPrelievo,
                    double importo, String casuale, Mezzo mezzo, Carta cartaEsecuzione) {
        super(dataTransazione, costoTransazione, esito);
        this.idPrelievo = idPrelievo;
        this.importo = importo;
        this.casuale = casuale;
        this.mezzo = mezzo;
        this.cartaEsecuzione = cartaEsecuzione;
    }

    public int getIdPrelievo() {return idPrelievo;}
    public double getImporto() {return importo;}
    public String getCasuale() {return casuale;}
    public Mezzo getMezzo() {return mezzo;}
    public Carta getCartaEsecuzione() {return cartaEsecuzione;}
}
