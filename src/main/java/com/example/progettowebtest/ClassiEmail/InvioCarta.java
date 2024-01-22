package com.example.progettowebtest.ClassiEmail;

public class InvioCarta {
    private String nomeCognome;
    private String pinCarta;
    private String numCarta;
    private String scadenzaCarta;
    private String cvv;
    private String tipo;

    public InvioCarta(String nomeCognome, String pinCarta, String numCarta, String scadenzaCarta, String cvv, String tipo) {
        this.nomeCognome = nomeCognome;
        this.pinCarta = pinCarta;
        this.numCarta = numCarta;
        this.scadenzaCarta = scadenzaCarta;
        this.cvv = cvv;
        this.tipo= tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public String getNomeCognome() {
        return nomeCognome;
    }

    public String getPinCarta() {
        return pinCarta;
    }

    public String getNumCarta() {
        return numCarta;
    }

    public String getScadenzaCarta() {
        return scadenzaCarta;
    }

    public String getCvv() {
        return cvv;
    }
}
