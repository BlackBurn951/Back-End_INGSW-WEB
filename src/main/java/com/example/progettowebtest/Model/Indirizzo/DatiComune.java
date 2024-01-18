package com.example.progettowebtest.Model.Indirizzo;

public class DatiComune {
    private int idComune;
    private String nomeComune;
    private String cap;
    private String provincia;
    private String regione;

    public DatiComune(int idComune, String nomeComune, String cap, String provincia, String regione) {
        this.idComune = idComune;
        this.nomeComune = nomeComune;
        this.cap = cap;
        this.provincia = provincia;
        this.regione = regione;
    }

    public int getIdComune() {return idComune;}
    public String getNomeComune() {return nomeComune;}
    public String getCap() {return cap;}
    public String getProvincia() {return provincia;}
    public String getRegione() {return regione;}
}
