package com.example.progettowebtest.Model.ContoCorrente;


public class Notifiche {
    private int id;
    private String notifica;
    private boolean letta;

    public Notifiche(String notifica, boolean letta) {
        this.notifica = notifica;
        this.letta = letta;
    }

    public int getId() {
        return id;
    }

    public String getNotifica() {
        return notifica;
    }

    public boolean isLetta() {
        return letta;
    }

    public void setId(int id) {
        this.id = id;
    }
}
