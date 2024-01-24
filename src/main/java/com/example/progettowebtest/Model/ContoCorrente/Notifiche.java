package com.example.progettowebtest.Model.ContoCorrente;


public class Notifiche {
    private int id=-1;
    private String notifica;
    private boolean letta;

    public Notifiche(String notifica, boolean letta) {
        this.notifica = notifica;
        this.letta = letta;
    }

    public int getId() {
        return id;
    }

    public String getTesto() {
        return notifica;
    }

    public boolean isLetta() {
        return letta;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void letta() {this.letta= true;}
}
