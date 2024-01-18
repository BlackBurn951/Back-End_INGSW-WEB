package com.example.progettowebtest.Model.Indirizzo;

public class Indirizzo {
    private TipoVia tipologiaVia;
    private String nomeVia;
    private String numCivico;
    private DatiComune comune;

    public Indirizzo(TipoVia tipologiaVia, String nomeVia, String numCivico, DatiComune comune) {
        this.tipologiaVia = tipologiaVia;
        this.nomeVia = nomeVia;
        this.numCivico = numCivico;
        this.comune = comune;
    }

    public TipoVia getTipologiaVia() {return tipologiaVia;}
    public String getNomeVia() {return nomeVia;}
    public String getNumCivico() {return numCivico;}
    public DatiComune getComune() {return comune;}
}
