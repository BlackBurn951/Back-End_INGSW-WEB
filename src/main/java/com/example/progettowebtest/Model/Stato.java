package com.example.progettowebtest.Model;

import java.sql.Date;

public class Stato {
    private int idStato;
    private String valoreStato;

    public Stato(int idStato, String valoreStato) {
        this.idStato = idStato;
        this.valoreStato = valoreStato;
    }

    public int getIdStato() {return idStato;}
    public String getValoreStato() {return valoreStato;}
}
