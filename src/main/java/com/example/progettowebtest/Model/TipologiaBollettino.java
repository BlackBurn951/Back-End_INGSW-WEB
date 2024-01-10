package com.example.progettowebtest.Model;

public class TipologiaBollettino {
    private int idTipoBol;
    private String tipo;

    public TipologiaBollettino(int idTipoBol, String tipo) {
        this.idTipoBol = idTipoBol;
        this.tipo = tipo;
    }

    public int getIdTipoBol() {return idTipoBol;}
    public String getTipo() {return tipo;}
}
