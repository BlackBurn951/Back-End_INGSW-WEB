package com.example.progettowebtest.Model;

import java.sql.Date;

public abstract class DatiUtente {
    private String nome;
    private String cognome;
    private String cittadinanza;
    private String comuneNascita;
    private String sesso;
    private String provNascita;
    private Date dataNascita;

    protected DatiUtente(String nome, String cognome, String cittadinanza, String comuneNascita, String sesso, String provNascita, String dataNascita) {
        this.nome = nome;
        this.cognome = cognome;
        this.cittadinanza = cittadinanza;
        this.comuneNascita = comuneNascita;
        this.sesso = sesso;
        this.provNascita = provNascita;
        this.dataNascita= Date.valueOf(dataNascita);
    }

    public String getNome() {return nome;}

    public String getCognome() {return cognome;}

    public String getCittadinanza() {return cittadinanza;}

    public String getComuneNascita() {return comuneNascita;}

    public String getSesso() {return sesso;}

    public String getProvNascita() {return provNascita;}

    public Date getDataNascita() {return dataNascita;}
}
