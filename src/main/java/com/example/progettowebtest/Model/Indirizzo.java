package com.example.progettowebtest.Model;

public class Indirizzo {
    private String tipoVia;
    private String nomeVia;
    private String numCivico;
    private String comune;
    private String cap;
    private String provincia;
    private String regione;

    public Indirizzo(String tipoVia, String nomeVia, String numCivico, String comune, String cap,
                     String provincia, String regione) {
        this.tipoVia = tipoVia;
        this.nomeVia = nomeVia;
        this.numCivico = numCivico;
        this.comune = comune;
        this.cap = cap;
        this.provincia = provincia;
        this.regione = regione;
    }
}
