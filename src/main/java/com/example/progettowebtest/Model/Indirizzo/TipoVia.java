package com.example.progettowebtest.Model.Indirizzo;

public class TipoVia {
    private int idVia;
    private String tipoVia;

    public TipoVia(int idVia, String tipoVia) {
        this.idVia = idVia;
        this.tipoVia = tipoVia;
    }

    public int getIdVia() {return idVia;}
    public String getTipoVia() {return tipoVia;}
}
