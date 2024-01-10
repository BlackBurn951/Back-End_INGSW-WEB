package com.example.progettowebtest.Model;

public class Mezzo {
    private int idMezzo;
    private String tipoMezzo;

    public Mezzo(int idMezzo, String tipoMezzo) {
        this.idMezzo = idMezzo;
        this.tipoMezzo = tipoMezzo;
    }

    public int getIdMezzo() {return idMezzo;}
    public String getTipoMezzo() {return tipoMezzo;}
}
