package com.example.progettowebtest.Model;

public abstract class DatiUtente {
    private String nome;
    private String cognome;
    private String cittadinanza;
    private String comuneNascita;
    private char sesso;
    private String provNascita;
    private String dataNascita;

    protected DatiUtente(String nome, String cognome, String cittadinanza, String comuneNascita, char sesso, String provNascita, String dataNascita) {
        this.nome = nome;
        this.cognome = cognome;
        this.cittadinanza = cittadinanza;
        this.comuneNascita = comuneNascita;
        this.sesso = sesso;
        this.provNascita = provNascita;
        this.dataNascita = dataNascita;
    }

    public String getNome() {return nome;}

    public String getCognome() {return cognome;}

    public String getCittadinanza() {return cittadinanza;}

    public String getComuneNascita() {return comuneNascita;}

    public char getSesso() {return sesso;}

    public String getProvNascita() {return provNascita;}

    public String getDataNascita() {return dataNascita;}
}
